package frc.team670.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.MustangMath;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.constants.ElevatorConstants;
import frc.team670.robot.constants.RobotPosition;
import org.littletonrobotics.junction.Logger;

public class Elevator extends MotorizedSubsytem {

  public static final float[] kSoftLimits = ElevatorConstants.kSoftLimits;

  private DigitalInput bottomLimitSwitch;
  private DigitalInput topLimitSwitch;

  private double height;

  private double mSetpoint;

  private TalonFX leadMotor;
  private TalonFX followerMotor;

  private double offset;

  private boolean hasBeenZeroed = false;
  public static boolean hasOverridedLimitSwitches = false;

  public ElevatorState state = ElevatorState.STOW;

  public enum ElevatorState {
    STOW(0),
    L4(1.629),
    L3(0),
    L2(0.5),
    L1(0.84),
    GROUND(0),
    ALGAE23(0),
    ALGAE12(0.637),
    BARGE(1.775),
    STATION(0.83782),
    PROCESSOR(0.186);

    private double height;

    private ElevatorState(double height) {
      this.height = height;
    }

    public double getHeight() {
      return this.height;
    }
  }

  private static Elevator mInstance;

  public static synchronized Elevator getInstance() {
    mInstance = mInstance == null ? new Elevator() : mInstance;
    return mInstance;
  }

  private Elevator() {
    leadMotor =
        TalonFXUtils.construct(
            ElevatorConstants.leadMotorID, ElevatorConstants.leadMotorConfiguration);
    followerMotor =
        TalonFXUtils.construct(
            ElevatorConstants.followerMotorID, ElevatorConstants.followerMotorConfiguration);

    registerMotors(leadMotor, followerMotor);

    followerMotor.setControl(
        new Follower(ElevatorConstants.leadMotorID, ElevatorConstants.kInvertFollower));

    bottomLimitSwitch = new DigitalInput(9);
    topLimitSwitch = new DigitalInput(8);

    // Start at 0
    leadMotor.setPosition(0);
  }

  public void setTargetHeight(RobotPosition robotPos) {
    setTargetHeightInMeters(robotPos.getElevatorHeight());
  }

  public boolean isBottomLimitSwitchTripped() {
    return hasOverridedLimitSwitches ? false : bottomLimitSwitch.get();
  }

  public boolean isTopLimitSwitchTripped() {
    return hasOverridedLimitSwitches ? false : topLimitSwitch.get();
  }

  public double getHeightInMeters() {
    height =
        (leadMotor.getRotorPosition().getValueAsDouble() / ElevatorConstants.kGearRatio)
            * (ElevatorConstants.kCircumferenceSprocket);
    return height;
  }

  private void setTargetHeightInMeters(double meters) {
    if (isTopLimitSwitchTripped()) {
      if (meters > this.getHeightInMeters()) {
        return;
      }
    }

    double oldSetpoint = mSetpoint;
    mSetpoint =
        MustangMath.getRotationsFromMeters(
            ElevatorConstants.kCircumferenceSprocket, gearRatio, meters);

    // just a precaution incase the operator tries to go too far down using manual
    // controls
    if (mSetpoint < 0) {
      mSetpoint = 0;
    }

    if ((oldSetpoint - mSetpoint > 0 && isBottomLimitSwitchTripped())
        || !checkSoftLimits(mSetpoint)) {
      mSetpoint = oldSetpoint;
      return;
    }
  }

  private void zeroElevator() {
    leadMotor.setPosition(0);
  }

  public boolean hasReachedTargetPosition() {

    return (MustangMath.doublesEqual(
        getHeightInMeters(),
        this.state.getHeight(),
        ElevatorConstants.ELEVATOR_ALLOWED_ERROR_IN_METERS));
  }

  public void addOffset(double offset) {
    this.offset += offset;
    this.setTargetHeightInMeters(RobotPosition.currentRobotPos.getElevatorHeight() + this.offset);
  }

  public void resetOffset() {
    this.offset = 0;
    this.setTargetHeightInMeters(RobotPosition.currentRobotPos.getElevatorHeight() + offset);
  }

  public boolean checkSoftLimits(double setpoint) {
    if (ElevatorConstants.kSoftLimits != null
        && (setpoint > ElevatorConstants.kSoftLimits[0]
            || setpoint < ElevatorConstants.kSoftLimits[1])) {
      return false;
    }
    return true;
  }

  public void stop() {
    leadMotor.set(0);
  }

  public void goToZero() {
    addOffset(-0.05);
  }

  @Override
  public void mustangPeriodic() {}

  @Override
  public void debugSubsystem() {
    Logger.recordOutput(this.getName() + "/CurrentHeightInMeters", getHeightInMeters());
    Logger.recordOutput(
        this.getName() + "/MetersSetpoint",
        MustangMath.getMetersFromRotations(
            ElevatorConstants.kCircumferenceSprocket, gearRatio, mSetpoint));
  }

  protected void checkInterference() {
    if (isBottomLimitSwitchTripped() && !hasBeenZeroed) {
      resetOffset();
      zeroElevator();

      hasBeenZeroed = true;
    } else if (!hasBeenZeroed) {
      goToZero();
      return;
    }

    if (isTopLimitSwitchTripped()) {
      stop();
      return;
    }

    double currentArmAngle = Arm.getInstance().getMotorPositionInDegrees();
    double currentTilterAngle = Tilter.getInstance().getMotorPositionInDegrees();
    // If no interference, continue moving
    if ((currentArmAngle > 0 && currentArmAngle < 90)
        || ((currentArmAngle > -50 && currentArmAngle < 180) && currentTilterAngle > 78)) {
      leadMotor.setControl(new MotionMagicVoltage(0).withPosition(mSetpoint));
    }
  }

  @Override
  public Health checkHealth() {
    return Health.GREEN;
  }
}

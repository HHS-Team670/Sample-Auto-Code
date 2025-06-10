package frc.team670.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.MustangMath;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.constants.RobotPosition;
import frc.team670.robot.constants.TilterConstants;
import org.littletonrobotics.junction.Logger;

public class Tilter extends MotorizedSubsytem {
  private TalonFX mMotor;

  private double mSetpoint;
  private static final double kNoSetPoint = 9999;
  ;
  private double offset = 0;

  private static Tilter mInstance;

  public static synchronized Tilter getInstance() {
    mInstance = mInstance == null ? new Tilter() : mInstance;
    return mInstance;
  }

  private Tilter() {
    this.mSetpoint = kNoSetPoint;

    mMotor = TalonFXUtils.construct(TilterConstants.kTilterMotorID, TilterConstants.motorConfig);

    mMotor.setPosition(0);

    registerMotors(mMotor);

    setGearRatio(TilterConstants.kGearRatio);
  }

  public void setTargetPosition(RobotPosition pos) {
    setMotorTarget(pos.getTilterAngle() + offset);
  }

  public void addOffset(double change) {
    offset += change;
    mSetpoint += MustangMath.getRotationsFromDegrees(gearRatio, change);
  }

  public boolean hasReachedTargetPosition() {
    return (MustangMath.doublesEqual(
        getMotorPostion(), mSetpoint, TilterConstants.kAllowedErrorRotations));
  }

  @Override
  public void mustangPeriodic() {}

  @Override
  public void debugSubsystem() {
    Logger.recordOutput(this.getName() + "/CurrentPositionDegrees", getMotorPositionInDegrees());
    Logger.recordOutput(
        this.getName() + "/SetpointDegrees",
        mSetpoint == kNoSetPoint ? -1 : MustangMath.getDegreesFromRotations(gearRatio, mSetpoint));
  }

  protected void checkInterference() {
    double currentArmAngle = Arm.getInstance().getMotorPositionInDegrees();
    // For the arm to not interfere with climb
    if (currentArmAngle < -65) {
      return;
    }
    // If interference, move away
    if (!Elevator.getInstance().hasReachedTargetPosition()
        && getMotorPositionInDegrees() < 78
        && currentArmAngle < 0) {
      setMotorTargetDegrees(87);
    } else if (Elevator.getInstance().hasReachedTargetPosition() || currentArmAngle < 180) {
      // Move claw toward setpoint
      setMotorTarget(mSetpoint);
    }
  }

  @Override
  public Health checkHealth() {
    if (mMotor == null || !mMotor.isAlive()) {
      return Health.RED;
    }
    return Health.GREEN;
  }
}

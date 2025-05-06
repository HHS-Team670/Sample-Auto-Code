package frc.team670.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.MustangMath;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.constants.ArmConstants;
import frc.team670.robot.constants.RobotPosition;

public class Arm extends MotorizedSubsytem {

  private TalonFX mMotor;

  private static final double kNoSetPoint = 9999;
  private double mSetpoint = kNoSetPoint;

  private double offset = 0;

  private static Arm mInstance = new Arm();

  private Arm() {
    mMotor = TalonFXUtils.construct(ArmConstants.kMotorID, ArmConstants.motorConfig);
    mMotor.setPosition(-26.985);
    registerMotors(mMotor);
    setGearRatio(ArmConstants.kGearRatio);
  }

  public static Arm getInstance() {
    return mInstance;
  }

  public void setTargetPosition(RobotPosition pos) {
    setMotorTargetDegrees(pos.getArmAngle() + offset);
  }

  public void addOffset(double change) {
    offset += change;
    mSetpoint += MustangMath.getDegreesFromRotations(gearRatio, change);
  }

  public boolean hasReachedTargetPosition() {
    return (MustangMath.doublesEqual(
        getMotorPostion(), mSetpoint, ArmConstants.kAllowedErrorDegrees));
  }

  @Override
  public Health checkHealth() {
    if (mMotor == null || !mMotor.isAlive()) {
      return Health.RED;
    }
    return Health.GREEN;
  }

  private void moveToTargetPosition(double positionInRotations) {
    mMotor.setControl(
        new MotionMagicVoltage(0)
            .withPosition(positionInRotations)
            .withSlot(0)
            .withFeedForward(
                ArmConstants.kG
                    * Math.cos(
                        Units.degreesToRadians(
                            360.0 * mMotor.getPosition().getValueAsDouble() / gearRatio))));
  }

  @Override
  public void debugSubsystem() {}

  public boolean clearSetpoint() {
    mSetpoint = kNoSetPoint;
    return true;
  }

  @Override
  protected void checkInterference() {
    if (!Elevator.getInstance().hasReachedTargetPosition()) {
      // If interference, move towards arm safe position
      if (getMotorPositionInDegrees() < -45 || getMotorPositionInDegrees() > 180) {
        moveToTargetPosition(
            mSetpoint > MustangMath.getRotationsFromDegrees(gearRatio, -40)
                ? mSetpoint
                : MustangMath.getRotationsFromDegrees(gearRatio, -40));
      }
    } else if ((mSetpoint != kNoSetPoint) && Elevator.getInstance().hasReachedTargetPosition()) {
      // Continue moving assuming there is a setpoint
      moveToTargetPosition(gearRatio);
    }
  }
}

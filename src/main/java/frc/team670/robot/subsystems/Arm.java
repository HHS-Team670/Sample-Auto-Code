package frc.team670.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.MustangMath;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.constants.ArmConstants;
import frc.team670.robot.constants.RobotPosition;
import org.littletonrobotics.junction.Logger;

public class Arm extends MotorizedSubsytem {

  private TalonFX mMotor;

  private static final double kNoSetPoint = 9999;
  private double mSetpoint = kNoSetPoint;

  private double offset = 0;

  private double feedForward = 0;

  private static Arm mInstance;

  public static synchronized Arm getInstance() {
    mInstance = mInstance == null ? new Arm() : mInstance;
    return mInstance;
  }

  private Arm() {
    mMotor = TalonFXUtils.construct(ArmConstants.kMotorID, ArmConstants.motorConfig);
    mMotor.setPosition(-26.985);
    registerMotors(mMotor);
    setGearRatio(ArmConstants.kGearRatio);
  }

  public void setTargetPosition(RobotPosition pos) {
    mSetpoint = MustangMath.getRotationsFromDegrees(gearRatio, pos.getArmAngle() + offset);
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
  public void mustangPeriodic() {
    feedForward =
        ArmConstants.kG
            * Math.cos(
                Units.degreesToRadians(
                    360.0 * mMotor.getPosition().getValueAsDouble() / gearRatio));
  }

  @Override
  public void debugSubsystem() {
    Logger.recordOutput(this.getName() + "/CurrentPositionDegrees", getMotorPositionInDegrees());
    Logger.recordOutput(
        this.getName() + "/SetpointDegrees",
        mSetpoint == kNoSetPoint ? -1 : MustangMath.getDegreesFromRotations(gearRatio, mSetpoint));
  }

  @Override
  protected void checkInterference() {
    if (!Elevator.getInstance().hasReachedTargetPosition()) {
      // If interference, move towards arm safe position
      if (getMotorPositionInDegrees() < -45 || getMotorPositionInDegrees() > 180) {
        setMotorTargetFeedForward(
            mSetpoint > MustangMath.getRotationsFromDegrees(gearRatio, -40)
                ? mSetpoint
                : MustangMath.getRotationsFromDegrees(gearRatio, -40),
            0,
            feedForward);
      }
    } else if ((mSetpoint != kNoSetPoint) && Elevator.getInstance().hasReachedTargetPosition()) {
      // Continue moving assuming there is a setpoint
      setMotorTargetFeedForward(mSetpoint, 0, feedForward);
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

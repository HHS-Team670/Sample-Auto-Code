package frc.team670.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose3d;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.MustangMath;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.Utilities.TypeUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.commands.MoveToRobotPosition;
import frc.team670.robot.constants.ClimbConstants;
import frc.team670.robot.constants.RobotPosition;
import org.littletonrobotics.junction.Logger;

public class Climb extends MotorizedSubsytem {

  private static Climb mInstance = new Climb();
  private TalonFX motor;
  private TalonFXConfiguration upConfig;
  private TalonFXConfiguration downConfig;

  private ClimbState climbState = ClimbState.STOW;

  public enum ClimbState {
    STOW(39),
    CLIMB(150),
    REST(0);

    private double position;

    private ClimbState(double position) {
      this.position = MustangMath.getDegreesFromRotations(position, ClimbConstants.GEAR_RATIO);
    }

    public double getPosition() {
      return this.position;
    }
  }

  public static Climb getInstance() {
    return mInstance;
  }

  public Climb() {
    motor = TalonFXUtils.construct(ClimbConstants.MOTOR_ID, ClimbConstants.upConfig, getName(), 0);
    registerMotors(motor);
    setGearRatio(ClimbConstants.GEAR_RATIO);
  }

  public void setClimbMode(ClimbState newState) {
    this.climbState = newState;

    if (newState == ClimbState.CLIMB || newState == ClimbState.REST) {
      TalonFXUtils.applyConfig(motor, downConfig);
    } else {
      TalonFXUtils.applyConfig(motor, upConfig);
    }
    moveClimb(newState);
  }

  private void moveClimb(ClimbState climbState) {
    motor.setControl(new MotionMagicVoltage(climbState.getPosition()).withSlot(0));
    if (climbState == ClimbState.CLIMB) {
      new MoveToRobotPosition(RobotPosition.STOW).schedule();
    }
  }

  @Override
  public Health checkHealth() {
    return Health.GREEN;
  }

  @Override
  public void debugSubsystem() {
    Logger.recordOutput(this.getName() + "/mode", climbState);
  }

  @Override
  protected void checkInterference() {}

  @Override
  public Pose3d calculateSimPose() {
    return new Pose3d();
    // return TypeUtils.unimplemented();
  }
}

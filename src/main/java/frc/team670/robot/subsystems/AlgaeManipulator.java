package frc.team670.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.Utilities.TypeUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.commands.MoveToRobotPosition;
import frc.team670.robot.constants.AlgaeManipulatorConstants;
import frc.team670.robot.constants.RobotPosition;
import frc.team670.robot.subsystems.Elevator.ElevatorState;
import org.littletonrobotics.junction.Logger;

public class AlgaeManipulator extends MotorizedSubsytem {
  Elevator mElevator = Elevator.getInstance();
  Arm mArm = Arm.getInstance();
  Tilter mTilter = Tilter.getInstance();

  public enum Mode {
    EJECTING,
    INTAKING,
    IDLE;
  }

  protected Timer m_timer = new Timer();

  private TalonFX mMotor;
  private AlgaeManipulator.Mode mode;

  private Debouncer encoderDebouncer = new Debouncer(0.5);

  private double ejectingSpeed = AlgaeManipulatorConstants.kEjectingSpeed;
  private double intakingSpeed = AlgaeManipulatorConstants.kRollingSpeed;
  private double idleSpeed = AlgaeManipulatorConstants.kIdleSpeed;

  private LED mLED = LED.getInstance();

  private static AlgaeManipulator mInstance;

  public static synchronized AlgaeManipulator getInstance() {
    mInstance = mInstance == null ? new AlgaeManipulator() : mInstance;
    return mInstance;
  }

  public AlgaeManipulator() {
    setGearRatio(AlgaeManipulatorConstants.kGearRatio1);
    mode = Mode.IDLE;
    mMotor =
        TalonFXUtils.construct(
            AlgaeManipulatorConstants.kMotorID,
            AlgaeManipulatorConstants.motorConfig,
            getName(),
            0);

    registerMotors(mMotor);
  }

  public void setAlgaeManipulatorMode(AlgaeManipulator.Mode mode) {
    this.mode = mode;
    switch (mode) {
      case INTAKING:
        intake();
        break;
      case EJECTING:
        eject();
        m_timer.start();
        break;
      case IDLE:
        idle();
        break;
      default:
        break;
    }
  }

  private void intake() {
    mMotor.set(intakingSpeed);
  }

  private void eject() {
    mMotor.set(ejectingSpeed);
  }

  private void idle() {
    mMotor.set(idleSpeed);
  }

  /** Checking for hardware breaks with the motor */
  @Override
  public Health checkHealth() {
    if (mMotor == null || !mMotor.isAlive()) {
      return Health.RED;
    }
    return Health.GREEN;
  }

  @Override
  public void periodic() {
    super.periodic();

    switch (mode) {
      case INTAKING:
        if (DriverStation.isTeleopEnabled()) {
          mLED.setLedMode(LED.Mode.INTAKING_ALGAE);
        }
        if (encoderDebouncer.calculate(
            mMotor.getStatorCurrent().getValueAsDouble() > AlgaeManipulatorConstants.kCurrentMax)) {
          setAlgaeManipulatorMode(AlgaeManipulator.Mode.IDLE);
        }
        break;
      case EJECTING:
        if (DriverStation.isTeleopEnabled()) {
          mLED.setLedMode(LED.Mode.EJECTING_ALGAE);
        }
        if (m_timer.hasElapsed(AlgaeManipulatorConstants.kEjectTime)) {
          m_timer.stop();
          m_timer.reset();
          mMotor.set(0);
          if (mElevator.state == ElevatorState.BARGE) {
            new MoveToRobotPosition(RobotPosition.STATION).schedule();
          }
        }
        break;
      case IDLE:
        break;
      default:
        mMotor.set(0);
    }
  }

  @Override
  protected void checkInterference() {}

  @Override
  public void debugSubsystem() {
    Logger.recordOutput(this.getName() + "/Mode", mode);
  }

  @Override
  public Pose3d calculateSimPose() {
    return new Pose3d();
    // return TypeUtils.unimplemented();
  }
}

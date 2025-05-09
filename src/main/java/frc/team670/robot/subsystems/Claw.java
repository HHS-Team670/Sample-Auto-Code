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
import frc.team670.robot.constants.ClawConstants;
import frc.team670.robot.subsystems.LED.LEDColor;
import org.littletonrobotics.junction.Logger;

public class Claw extends MotorizedSubsytem {
  private boolean hasCoral = false;

  public enum Status {
    EJECTING,
    INTAKING,
    IDLE;
  }

  protected Timer m_timer = new Timer();

  private TalonFX mMotor;
  private Claw.Status status;

  private Debouncer encoderDebouncer = new Debouncer(0.5);
  private Debouncer encoderDebouncerAuto = new Debouncer(0.3);

  private double ejectingSpeed = ClawConstants.kEjectingSpeed;
  private double intakingSpeed = ClawConstants.kRollingSpeed;
  private double idleSpeed = ClawConstants.kIdleSpeed;

  private LED led = LED.getInstance();

  private static Claw mInstance = new Claw();

  private double motorSpeed = 0;

  public static Claw getInstance() {
    return mInstance;
  }

  public Claw() {
    status = Status.IDLE;
    mMotor =
        TalonFXUtils.construct(ClawConstants.kMotorID, ClawConstants.motorConfig, getName(), 0);

    registerMotors(mMotor);

    setGearRatio(1);
  }

  public boolean hasCoral() {
    return hasCoral;
  }

  public void setClawMode(Claw.Status status) {
    this.status = status;
    switch (status) {
      case INTAKING:
        m_timer.stop();
        m_timer.reset();
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
    encoderDebouncer = new Debouncer(0.5);
    encoderDebouncerAuto = new Debouncer(0.3);
    mMotor.set(intakingSpeed);
    motorSpeed = intakingSpeed;
  }

  private void eject() {
    mMotor.set(ejectingSpeed);
    hasCoral = false;
    motorSpeed = ejectingSpeed;
  }

  private void idle() {
    mMotor.set(idleSpeed);
    motorSpeed = idleSpeed;
  }

  /** Checking for hardware breaks with the mMotor */
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

    switch (status) {
      case INTAKING:
        if (DriverStation.isAutonomousEnabled()) {
          if (encoderDebouncerAuto.calculate(mMotor.getVelocity().getValueAsDouble() < 1)) {
            hasCoral = true;
            setClawMode(Claw.Status.IDLE);
          }
        } else if (encoderDebouncer.calculate(mMotor.getVelocity().getValueAsDouble() < 1)) {
          hasCoral = true;
          setClawMode(Claw.Status.IDLE);
          if (DriverStation.isTeleopEnabled()) {
            led.solidhsv(LEDColor.GREEN);
          }
        }
        break;
      case EJECTING:
        if (m_timer.hasElapsed(ClawConstants.kEjectTime)) {
          m_timer.stop();
          m_timer.reset();
          mMotor.set(0);
          motorSpeed = 0;
          if (DriverStation.isTeleopEnabled()) {
            led.solidhsv(LEDColor.YELLOW);
          }
        }
        break;
      default:
        mMotor.set(0);
        break;
    }
  }

  @Override
  public void debugSubsystem() {
    Logger.recordOutput(this.getName() + "/CurrentSpeed", motorSpeed);
  }

  @Override
  protected void checkInterference() {}

  @Override
  public Pose3d calculateSimPose() {
    return TypeUtils.unimplemented();
  }
}

package frc.team670.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.TalonFXUtils;
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
    private final String clawStateKey = "Claw/State";

    private Debouncer encoderDebouncer = new Debouncer(0.5);
    private Debouncer encoderDebouncerAuto = new Debouncer(0.3);

    private double ejectingSpeed = ClawConstants.kEjectingSpeed;
    private double intakingSpeed = ClawConstants.kRollingSpeed;
    private double idleSpeed = ClawConstants.kIdleSpeed;

    private LED led = LED.getInstance();

    private static Claw mInstance = new Claw();

    public static Claw getInstance() {
        return mInstance;
    }

    public Claw() {
        status = Status.IDLE;
        mMotor = TalonFXUtils.construct(ClawConstants.kMotorID, ClawConstants.motorConfig);
        motors.add(mMotor);
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
        Logger.recordOutput("Claw/Speed", intakingSpeed);
        encoderDebouncer = new Debouncer(0.5);
        encoderDebouncerAuto = new Debouncer(0.3);
        mMotor.set(intakingSpeed);
    }

    private void eject() {
        Logger.recordOutput("Claw/Speed", ejectingSpeed);
        mMotor.set(ejectingSpeed);
        hasCoral = false;
    }

    private void idle() {
        Logger.recordOutput("Claw/Speed", idleSpeed);
        mMotor.set(0);
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
        Logger.recordOutput(clawStateKey, status.toString());
        Logger.recordOutput("Claw/has coral", hasCoral);
        Logger.recordOutput("Claw/Velocity", mMotor.getVelocity().getValueAsDouble());
        Logger.recordOutput("Claw/Current", mMotor.getStatorCurrent().getValueAsDouble());
    }

    @Override
    protected void checkInterference() {
    }
}
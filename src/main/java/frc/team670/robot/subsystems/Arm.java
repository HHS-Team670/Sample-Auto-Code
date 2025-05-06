package frc.team670.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.MustangMath;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.constants.ArmConstants;

import org.littletonrobotics.junction.Logger;

public class Arm extends MotorizedSubsytem {
    private String ARM_STATUS = "START";

    private TalonFX mMotor;

    private static final double kNoSetPoint = 9999;
    private double mSetpoint = kNoSetPoint;

    public boolean armRunning = false;
    private Position armPosition = Position.REST;

    private double offset = 0;

    private static Arm mInstance = new Arm();

    public Arm() {
        mMotor = TalonFXUtils.construct(ArmConstants.kMotorID, ArmConstants.motorConfig);
        mMotor.setPosition(-26.985);
        registerMotors(mMotor);

        motors.add(mMotor);
    }

    public boolean getTimeout() {
        return false;
    }

    public static Arm getInstance() {
        return mInstance;
    }

    public enum Position {
        L1,
        L2,
        L3,
        L4,
        ALGAE12,
        ALGAE23,
        GROUND,
        STATION,
        REST,
        BARGE,
        PROCESSOR,
        STOW
    }

    public void setPos(Position pos) {
        ARM_STATUS = "SetPos";
        this.armPosition = pos;
        switch (pos) {
            case L1:
                setSystemTargetAngleInDegrees(220.393);
                break;
            case L2:
                setSystemTargetAngleInDegrees(-86.047396);
                break;
            case L3:
                setSystemTargetAngleInDegrees(39);
                break;
            case L4:
                setSystemTargetAngleInDegrees(36.32590659340659);
                break;
            case GROUND:
                setSystemTargetAngleInDegrees(-58.881);
                break;
            case BARGE:
                setSystemTargetAngleInDegrees(61.749);
                break;
            case ALGAE12:
                setSystemTargetAngleInDegrees(-44.51);
                break;
            case ALGAE23:
                setSystemTargetAngleInDegrees(28.199);
                break;
            case STATION:
                setSystemTargetAngleInDegrees(208.236);
                break;
            case PROCESSOR:
                setSystemTargetAngleInDegrees(202.113);
                break;
            case STOW:
                setSystemTargetAngleInDegrees(-90);
                break;
            default:
                break;
        }
    }

    static final double h = ((-26.985) / ArmConstants.kGearRatio) * 360.0;

    private boolean setSystemTargetAngleInDegrees(double angleNoOffset) {
        ARM_STATUS = "SetSystemTargetAngleInDegrees";
        double angle = angleNoOffset + offset;
        double setpoint = getMotorRotationsFromAngle(angle);
        // if (checkSoftLimits(setpoint)) {
        setSystemMotionTarget(setpoint);
        return true;
        // }
        // return false;
    }

    public void addOffset(double change) {
        offset += change;
        setSystemTargetAngleInDegrees(225 + offset);
    }

    private double getMotorRotationsFromAngle(double angle) {
        double rotations = (angle / 360) * ArmConstants.kGearRatio;

        return rotations;
    }

    private boolean setSystemMotionTarget(double setpoint) {
        ARM_STATUS = "SetSystemMotionTarget1";
        setSystemMotionTarget(setpoint, 0);
        return true;
    }

    private boolean setSystemMotionTarget(double setpoint, double arbitraryFF) {
        ARM_STATUS = "SetSystemMotionTarget2";

        if (checkSoftLimits(setpoint)) {
            this.mSetpoint = setpoint;
            return true;
        }
        return false;
    }

    private boolean checkSoftLimits(double setpoint) {
        if (ArmConstants.kSoftLimits != null
                && (setpoint > ArmConstants.kSoftLimits[0]
                        || setpoint < ArmConstants.kSoftLimits[1])) {
            Logger.recordOutput("Arm/InsideSoftLimits", false);
            return false;
        }
        Logger.recordOutput("Arm/InsideSoftLimits", true);
        return true;
    }

    public Position getPos() {
        return armPosition;
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

    private void moveTo(double positionInRotations) {
        mMotor.setControl(
                new MotionMagicVoltage(0)
                        .withPosition(positionInRotations)
                        .withSlot(0)
                        .withFeedForward(
                                ArmConstants.kG
                                        * Math.cos(
                                                Units.degreesToRadians(
                                                        360.0
                                                                * mMotor.getPosition().getValueAsDouble()
                                                                / ArmConstants.kGearRatio))));
    }

    public void periodic() {
        checkInterference();
    }

    @Override
    public void debugSubsystem() {
        if (mSetpoint != kNoSetPoint) {
            Logger.recordOutput("Arm/Setpoint", mSetpoint);
        }
        Logger.recordOutput("Arm/ArmAngle", getMotorPositionInDegrees());

        if (mSetpoint != kNoSetPoint) {
            Logger.recordOutput("Arm/Running", hasReachedTargetPosition() == true);
            Logger.recordOutput("Arm/Position", armPosition);
            Logger.recordOutput("Arm/Status", ARM_STATUS);
            Logger.recordOutput("Arm/Current", this.mMotor.getStatorCurrent().getValueAsDouble());
            Logger.recordOutput("Arm/Error", mMotor.getClosedLoopError().getValueAsDouble());
        }
    }

    public boolean clearSetpoint() {
        if (checkSoftLimits(0)) {
            mSetpoint = kNoSetPoint;
            return true;
        }
        return false;
    }

    @Override
    protected void checkInterference() {
        if (!Elevator.getInstance().hasReachedTargetPosition()) {
            // If interference, move towards arm safe position
            if (getMotorPositionInDegrees() < -45 || getMotorPositionInDegrees() > 180) {
                Logger.recordOutput("Arm/ArmSafe", false);
                moveTo(
                        mSetpoint > getMotorRotationsFromAngle(-40)
                                ? mSetpoint
                                : getMotorRotationsFromAngle(-40));
            }
        } else if ((mSetpoint != kNoSetPoint) && Elevator.getInstance().hasReachedTargetPosition()) {
            Logger.recordOutput("Arm/ArmSafe", true);
            // Continue moving assuming there is a setpoint
            moveTo(mSetpoint);
        } else {
            Logger.recordOutput("Arm/ArmSafe", true);
        }
    }

}
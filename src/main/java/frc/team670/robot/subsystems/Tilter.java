package frc.team670.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.team670.libs.Health.Health;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.constants.TilterConstants;

public class Tilter extends MotorizedSubsytem {
    private TalonFX mMotor;

    private double mSetpoint;
    private static final double kNoSetPoint = 9999;;
    private double offset = 0;

    public enum Position {
        STATION,
        REEF,
        SAFEREEF,
        L1,
        L2,
        L3,
        L4,
        ALGAE12,
        ALGAE23,
        PROCESSOR,
        BARGE,
        CLIMB,
        DEFAULT,
    }

    private Tilter.Position pos = Position.DEFAULT;

    private static Tilter mInstance = new Tilter();

    public static Tilter getInstance() {
        return mInstance;
    }

    public Tilter() {
        this.mSetpoint = kNoSetPoint;

        mMotor = TalonFXUtils.construct(TilterConstants.kTilterMotorID, TilterConstants.motorConfig);

        mMotor.setPosition(0);

        motors.add(mMotor);
    }

    public Tilter.Position getPos() {
        return pos;
    }

    public void setPos(Tilter.Position newPos) {
        Logger.recordOutput(
                "Current state", String.format("Incomming state %s and current state %s", newPos, pos));
        pos = newPos;
        switch (pos) {
            // The following are guess angles
            case STATION:
                setSystemTargetAngleInDegrees(-64.26269);
                break;
            case L1:
                setSystemTargetAngleInDegrees(-46.766);
                break;
            case L2:
                setSystemTargetAngleInDegrees(78.93457);
                break;
            case L3:
                setSystemTargetAngleInDegrees(-85);
                break;
            case L4:
                setSystemTargetAngleInDegrees(-92);
                break;
            case ALGAE12:
                setSystemTargetAngleInDegrees(86.13);
                break;
            case ALGAE23:
                setSystemTargetAngleInDegrees(27);
                break;
            case PROCESSOR:
                setSystemTargetAngleInDegrees(96.123);
                break;
            case BARGE:
                setSystemTargetAngleInDegrees(89.211);
                break;
            case CLIMB:
                setSystemTargetAngleInDegrees(90);
                break;
            default:
                setSystemTargetAngleInDegrees(0);
                break;
        }
    }

    @Override
    public Health checkHealth() {
        if (mMotor == null || !mMotor.isAlive()) {
            return Health.RED;
        }
        return Health.GREEN;
    }



    @Override
    public void debugSubsystem() {
        Logger.recordOutput("Tilter/TilterCurrentAngle", this.getCurrentAngleInDegrees());
        if (mSetpoint != kNoSetPoint) {
            Logger.recordOutput("Tilter/setpoint", mSetpoint);
        }
        Logger.recordOutput("Tilter/degreesSetpoint", getAngleFromMotorRotations(mSetpoint));
        Logger.recordOutput("Tilter/positionRotations", getUnadjustedPosition());
        Logger.recordOutput("Tilter/Current", mMotor.getStatorCurrent().getValueAsDouble());
    }

    @Override
    public void periodic() {
        checkInterference();
    }

    private void setSystemTargetAngleInDegrees(double angleNoOffset) {
        double angle = angleNoOffset + offset;
        double setpoint = getMotorRotationsFromAngle(angle);
        if (checkSoftLimits(setpoint)) {
            setSystemMotionTarget(getMotorRotationsFromAngle(angle));
        }
    }

    public void addOffset(double change) {
        offset += change;
        setSystemMotionTarget(mSetpoint + getMotorRotationsFromAngle(change));
        Logger.recordOutput("Tilter/Offset", offset);
    }

    public boolean hasReachedTargetPosition() {
        return (MathUtils.doublesEqual(
                getUnadjustedPosition(), mSetpoint, TilterConstants.kAllowedErrorRotations));
    }

    private double getMotorRotationsFromAngle(double angle) {
        double rotations = (angle / 360) * TilterConstants.kGearRatio;
        return rotations;
    }

    private double getAngleFromMotorRotations(double rotations) {
        double angle = rotations / TilterConstants.kGearRatio * 360;
        return angle;
    }

    public double getCurrentAngleInDegrees() {
        double rotations = getUnadjustedPosition();
        double angle = 360 * ((rotations) / TilterConstants.kGearRatio);
        return angle % 360;
    }

    private boolean checkSoftLimits(double setpoint) {
        if (TilterConstants.kSoftLimits != null
                && (setpoint > TilterConstants.kSoftLimits[0]
                        || setpoint < TilterConstants.kSoftLimits[1])) {
            // ConsoleLogger.consoleError(
            //         "In "
            //                 + getName()
            //                 + " Improper setpoint: "
            //                 + setpoint
            //                 + " Setpoint should be between "
            //                 + TilterConstants.kSoftLimits[1]
            //                 + " and "
            //                 + TilterConstants.kSoftLimits[0]);
            return false;
        }
        return true;
    }

    private double getUnadjustedPosition() {
        return this.mMotor.getPosition().getValueAsDouble();
    }

    private void setSystemMotionTarget(double setpoint) {
        if (true) {
            setSystemMotionTarget(setpoint, 0);
        }
    }

    protected void checkInterference() {
        double currentArmAngle = Arm.getInstance().getMotorPositionInDegrees();
        // For the arm to not interfere with climb
        if (currentArmAngle < -65) {
            return;
        }
        // If interference, move away
        if (!Elevator.getInstance().hasReachedTargetPosition()
                && this.getCurrentAngleInDegrees() < 78
                && currentArmAngle < 0) {
            mMotor.setControl(
                    new MotionMagicVoltage(0).withPosition(getMotorRotationsFromAngle(87)).withSlot(0));
        } else if (Elevator.getInstance().hasReachedTargetPosition() || currentArmAngle < 180) {
            Logger.recordOutput("Tilter/SafePos", true);
            // Move claw toward setpoint
            mMotor.setControl(new MotionMagicVoltage(0).withPosition(mSetpoint).withSlot(0));
        } else {
            Logger.recordOutput("Tilter/SafePos", true);
        }
    }

    private void setSystemMotionTarget(double setpoint, double arbitraryFF) {
        Logger.recordOutput("Tiliter/posSet", "null");
        this.mSetpoint = setpoint;
        checkInterference();
    }

    public boolean clearSetpoint() {
        if (checkSoftLimits(0)) {
            mSetpoint = kNoSetPoint;
            return true;
        }
        return false;
    }
}
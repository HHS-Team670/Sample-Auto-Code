
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

import org.littletonrobotics.junction.Logger;

public class Elevator extends MotorizedSubsytem {

    public static final float[] kSoftLimits = ElevatorConstants.kSoftLimits;

    private DigitalInput bottomLimitSwitch;
    private DigitalInput topLimitSwitch;

    private static Elevator mInstance = new Elevator();

    public ElevatorState state = ElevatorState.STOW;
    private double height;
    private double mSetpoint;
    private double targetPosition;
    private TalonFX leadMotor;
    private TalonFX followerMotor;
    private double offset;
    private boolean hasBeenZeroed = false;
    public static boolean hasOverridedLimitSwitches = false;

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

    private Elevator() {

        targetPosition = 0;

        leadMotor = TalonFXUtils.construct(ElevatorConstants.leadMotorID, ElevatorConstants.leadMotorConfiguration);
        followerMotor = TalonFXUtils.construct(ElevatorConstants.followerMotorID,
                ElevatorConstants.followerMotorConfiguration);

        registerMotors(leadMotor, followerMotor);

        followerMotor.setControl(new Follower(ElevatorConstants.leadMotorID, ElevatorConstants.kInvertFollower));

        bottomLimitSwitch = new DigitalInput(9);
        topLimitSwitch = new DigitalInput(8);

        // Start at 0
        leadMotor.setPosition(0);

        motors.add(leadMotor);
        motors.add(followerMotor);
    }

    public static Elevator getInstance() {
        return mInstance;
    }

    public boolean isBottomLimitSwitchTripped() {
        return hasOverridedLimitSwitches ? false : bottomLimitSwitch.get();
    }

    public boolean isTopLimitSwitchTripped() {
        return hasOverridedLimitSwitches ? false : topLimitSwitch.get();
    }

    public double getHeightInMeters() {
        height = (leadMotor.getRotorPosition().getValueAsDouble() / ElevatorConstants.kGearRatio)
                * (ElevatorConstants.kCircumferenceSprocket);
        return height;
    }

    private void setHeightInMeters(double meters) {
        if (isTopLimitSwitchTripped()) {
            if (meters > this.getHeightInMeters()) {
                return;
            }
        }
        // ConsoleLogger.consoleLog("IT HAS SET POSITION");

        double oldSetpoint = mSetpoint;
        mSetpoint = (meters / ElevatorConstants.kCircumferenceSprocket)
                * (ElevatorConstants.kGearRatio);
        // mSetpoint = meters;
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

        targetPosition = meters;

        Logger.recordOutput(this.getName() + "/Motor Rotations", mSetpoint);
        Logger.recordOutput(this.getName() + "/Target Position", targetPosition);
        if (this.hasBeenZeroed) {
            checkInterference();
        }
    }

    protected void checkInterference() {
        if (!hasBeenZeroed) {
            return;
        }
        double currentArmAngle = Arm.getInstance().getMotorPositionInDegrees();
        double currentTilterAngle = Tilter.getInstance().getMotorPositionInDegrees();
        // If no interference, continue moving
        if ((currentArmAngle > 0 && currentArmAngle < 90)
                || ((currentArmAngle > -50 && currentArmAngle < 180) && currentTilterAngle > 78)) {
            leadMotor.setControl(new MotionMagicVoltage(0).withPosition(mSetpoint).withSlot(0));
        }
    }

    public void setState(ElevatorState state) {
        setHeightInMeters(state.getHeight());
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
        this.setHeightInMeters(this.state.getHeight() + this.offset);
    }

    public void resetOffset() {
        this.offset = 0;
        this.setHeightInMeters(this.state.getHeight() + offset);
    }

    public void moveToTarget(ElevatorState state) {
        resetOffset();
        this.state = state;
        this.setHeightInMeters(state.getHeight() + offset);
    }

    @Override
    public Health checkHealth() {
        return Health.GREEN;
    }

    public void goToZero() {
        addOffset(-0.05);
    }

    public boolean checkSoftLimits(double setpoint) {
        if (ElevatorConstants.kSoftLimits != null
                && (setpoint > ElevatorConstants.kSoftLimits[0]
                        || setpoint < ElevatorConstants.kSoftLimits[1])) {
            return false;
        }
        return true;
    }

    @Override
    public void periodic() {
        super.periodic();

        if (isBottomLimitSwitchTripped() && !hasBeenZeroed) {
            resetOffset();
            zeroElevator();

            hasBeenZeroed = true;
        } else if (!hasBeenZeroed) {
            goToZero();
        }

        if (isTopLimitSwitchTripped()) {
            stop();
        } else {
            checkInterference();
        }
    }

    public void stop() {
        leadMotor.set(0);
    }

    public void setHaBeenZeroedToFalse() {
        hasBeenZeroed = false;
    }

    @Override
    public void debugSubsystem() {
        Logger.recordOutput(this.getName() + "/height", getHeightInMeters());
        Logger.recordOutput(this.getName() + "/bottomSwitch", isBottomLimitSwitchTripped());
        Logger.recordOutput(this.getName() + "/topSwitch", isTopLimitSwitchTripped());
        Logger.recordOutput("Elevator/State", state);
    }

}

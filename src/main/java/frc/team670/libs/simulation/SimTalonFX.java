package frc.team670.libs.simulation;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.MathUtil;

public class SimTalonFX {
    public static List<SimTalonFX> simMotors = new ArrayList<>();

    private final TalonFX motor;
    private final TalonFXSimState simState;

    private double simVelocity = 0.0; // rotations/sec
    private double targetPosition = 0.0; // rotations

    private double speed = 0.0;

    private boolean useTarget = true;

    private final double maxVelocity; // rotations/sec
    private final double maxAcceleration; // rotations/sec^2

    public SimTalonFX(TalonFX motor, double maxVelocity, double maxAcceleration) {
        this.motor = motor;
        this.simState = motor.getSimState();
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
        simMotors.add(this);
    }

    public void setTargetPosition(double rotations) {
        this.targetPosition = rotations;
        useTarget = true;
    }

    public void setSpeed(double rotationsPerSecond) {
        this.speed = rotationsPerSecond;
        useTarget = false;
    }

    public void update(double dtSeconds) {
        if (useTarget) {
            double error = targetPosition - motor.getPosition().getValueAsDouble();
            double direction = Math.signum(error);
            double distanceRemaining = Math.abs(error);

            // Compute the velocity needed to stop at the target
            double maxReachableVelocity = Math.sqrt(2 * maxAcceleration * distanceRemaining);
            double targetVelocity = Math.min(maxVelocity, maxReachableVelocity);
            targetVelocity *= direction;

            // Accelerate toward target velocity
            double accel = targetVelocity - simVelocity;
            accel = MathUtil.clamp(accel, -maxAcceleration * dtSeconds, maxAcceleration * dtSeconds);

            simVelocity += accel;

            // Write values into simulation
            simState.addRotorPosition(simVelocity * dtSeconds);
            simState.setRotorVelocity(simVelocity);
        } else {
            double accel = speed - simVelocity;
            accel = MathUtil.clamp(accel, -maxAcceleration * dtSeconds, maxAcceleration * dtSeconds);

            simVelocity += accel;

            // Write values into simulation
            simState.addRotorPosition(simVelocity * dtSeconds);
            simState.setRotorVelocity(simVelocity);
        }
    }

    public double getSimPosition() {
        return motor.getPosition().getValueAsDouble();
    }

}

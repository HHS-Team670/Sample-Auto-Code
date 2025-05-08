package frc.team670.libs.simulation;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;
import edu.wpi.first.math.MathUtil;
import java.util.ArrayList;
import java.util.List;

public class SimTalonFX {
  public static List<SimTalonFX> simMotors = new ArrayList<>();

  private final TalonFX motor;
  private final TalonFXSimState simState;

  private double simVelocity = 0.0; // rotations/sec
  private double targetPosition = 0.0; // rotations

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
  }

  public void update(double dtSeconds) {
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
  }

  public double getSimPosition() {
    return motor.getPosition().getValueAsDouble();
  }
}

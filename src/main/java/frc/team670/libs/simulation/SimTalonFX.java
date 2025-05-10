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

  private boolean isInvered;
  private boolean hasTarget = false;

  public String name;

  public SimTalonFX(
      TalonFX motor,
      double maxVelocity,
      double maxAcceleration,
      String name,
      double startPosition,
      boolean isInvered) {
    this.motor = motor;
    this.simState = motor.getSimState();
    this.maxVelocity = maxVelocity;
    this.maxAcceleration = maxAcceleration;
    this.name = name;
    this.isInvered = isInvered;
    simMotors.add(this);
    setStartPosition(startPosition);
  }

  /**
   * The setTargetPosition function sets the target position to a specified number
   * of rotations and
   * marks that a target has been set.
   * 
   * @param rotations The `setTargetPosition` method sets the target position for
   *                  a specific object.
   *                  The parameter `rotations` represents the number of rotations
   *                  that the motor should move to in the simulation
   */
  public void setTargetPosition(double rotations) {
    this.targetPosition = rotations;
    hasTarget = true;
  }

  /**
   * The setStartPosition function initializes the target position and sets the
   * simulation velocity to
   * zero.
   * 
   * @param start The `start` parameter in the `setStartPosition` method is a
   *              double value that
   *              represents the target position in rotations where you want to
   *              set the start
   *              position of the simulated motor.
   */
  public void setStartPosition(double start) {
    targetPosition = start;
    simVelocity = 0.0;
  }

  /**
   * The update function calculates and applies acceleration to reach a target
   * position with a maximum
   * velocity and acceleration while considering direction and error margin.
   * 
   * @param dtSeconds The paramete takes in delta time which represents the time
   *                  elapsed in
   *                  seconds since the last
   *                  update. It is used to calculate the changes in velocity and
   *                  position over a given time interval in
   *                  the `update` method.
   */
  public void update(double dtSeconds) {

    if (hasTarget) {
      double error = targetPosition - motor.getPosition().getValueAsDouble();
      if (Math.abs(error) < 0.05) {
        error = 0;
      }
      double direction = Math.signum(error);
      if (isInvered) {
        direction = -direction;
      }
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
  }

  /**
   * This Java function returns the simulated position value of a motor as a
   * double.
   * 
   * @return The method `getSimPosition()` is returning the encoder position value
   *         of a
   *         motor as a `double`
   *         data type.
   */
  public double getSimPosition() {
    return motor.getPosition().getValueAsDouble();
  }

  /**
   * The clearSetpoint function sets the hasTarget variable to false.
   * which prevents the update function from running until a new setpoint has been
   * set
   */
  public void clearSetpoint() {
    hasTarget = false;
  }

  /**
   * The function `getSimTarget()` returns the target position in rotations.
   * 
   * @return The method `getSimTarget()` is returning the value of the variable
   *         `targetPosition`, which
   *         is in rotations.
   */
  public double getSimTarget() {
    return targetPosition;
  }
}

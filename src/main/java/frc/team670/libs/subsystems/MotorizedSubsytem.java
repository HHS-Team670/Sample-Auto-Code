package frc.team670.libs.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Health.HealthChecker;
import frc.team670.libs.Utilities.MustangMath;

import java.util.ArrayList;
import java.util.List;

public abstract class MotorizedSubsytem implements HealthySubsytem, Subsystem, DebugSubsytem {

  protected List<TalonFX> motors = new ArrayList<>();

  protected int gearRatio;

  public List<TalonFX> getMotors() {
    return motors;
  }

  protected abstract void checkInterference();

  protected void registerMotors(TalonFX... motor) {
    for (TalonFX m : motor) {
      motors.add(m);
    }
  }

  @Override
  public void periodic() {
    HealthChecker.reportHealth(this, checkHealth());
    checkInterference();
    debugSubsystem();
  }

  /**
   * This Java function returns the rotations of the first motor in a list of
   * motors
   * 
   * @return The method `getMotorPosition()` is returning the rotations of the
   *         first motor in the
   *         list of motors
   */
  protected double getMotorPostion() {
    return motors.get(0).getPosition().getValueAsDouble();
  }

  /**
   * The function returns the motor position in degrees by converting rotations to
   * degrees using a gear
   * ratio.
   * 
   * @return The method `getMotorPositionInDegrees` is returning the motor
   *         position in degrees by
   *         converting the motor position (obtained from `getMotorPosition()`) to
   *         degrees using the gear ratio
   *         and a method from the `MustangMath` class.
   */
  protected double getMotorPositionInDegrees() {
    return MustangMath.getDegreesFromRotations(gearRatio, getMotorPostion());
  }

}

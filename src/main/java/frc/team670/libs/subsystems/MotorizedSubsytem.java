package frc.team670.libs.subsystems;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Health.HealthChecker;
import frc.team670.libs.Utilities.MustangMath;
import java.util.ArrayList;
import java.util.List;

public abstract class MotorizedSubsytem implements HealthySubsytem, Subsystem, DebugSubsytem {

  protected List<TalonFX> motors = new ArrayList<>();

  protected double gearRatio;

  /**
   * The function returns a list of TalonFX motors.
   *
   * @return A List of TalonFX motors is being returned.
   */
  public List<TalonFX> getMotors() {
    return motors;
  }

  /**
   * The function returns a TalonFX motor from the specified index.
   *
   * @return A TalonFX motor is being returned.
   */
  public TalonFX getMotors(int index) {
    return motors.get(index);
  }

  /**
   * The function "checkInterference" is a method that needs to be implemented by subclasses and is
   * used to check for interference between subsytems.
   */
  protected abstract void checkInterference();

  /**
   * The `registerMotors` method in Java adds TalonFX motors to a list. This is used to refrence the
   * motor in calcuations
   */
  protected void registerMotors(TalonFX... motor) {
    for (TalonFX m : motor) {
      motors.add(m);
    }
  }

  /**
   * The function `setGearRatio` sets the gear ratio to a specified value.
   *
   * @param value The `value` parameter in the `setGearRatio` method represents the new gear ratio
   *     that you want to set for the object. This value will be assigned to the `gearRatio`
   *     instance variable of the object.
   */
  protected void setGearRatio(double value) {
    this.gearRatio = value;
  }

  @Override
  public void periodic() {
    HealthChecker.reportHealth(this, checkHealth());
    checkInterference();
    debugSubsystem();
  }

  /**
   * This Java function returns the rotations of the first motor in a list of motors
   *
   * @return The method `getMotorPosition()` is returning the rotations of the first motor in the
   *     list of motors
   */
  public double getMotorPostion() {
    return motors.get(0).getPosition().getValueAsDouble();
  }

  /**
   * The function returns the motor position in degrees by converting rotations to degrees using a
   * gear ratio.
   *
   * @return The method `getMotorPositionInDegrees` is returning the motor position in degrees by
   *     converting the motor position (obtained from `getMotorPosition()`) to degrees using the
   *     gear ratio and a method from the `MustangMath` class.
   */
  public double getMotorPositionInDegrees() {
    return MustangMath.getDegreesFromRotations(gearRatio, getMotorPostion());
  }

  /**
   * The function sets the target position for a motor using Motion Magic control mode.
   *
   * @param rotations The `rotations` parameter represents the target number of rotations that you
   *     want the motor at the specified index to move to. This method sets the motor target
   *     position using Motion Magic control mode with the specified number of rotations.
   * @param index The `index` parameter is an integer value that represents the index of the motor
   *     in the list of motors. It is used to identify which motor you want to set the target for in
   *     the `motors` list.
   */
  public void setMotorTarget(double rotations, int index) {
    motors.get(index).setControl(new MotionMagicVoltage(0).withPosition(rotations));
  }

  /**
   * The setMotorTarget method sets the target rotations for a motor
   *
   * @param rotations the target postion for the motor in roations defaults to the motor at index 0
   *     the first motor in `registerMotors()`
   */
  public void setMotorTarget(double rotations) {
    setMotorTarget(rotations, 0);
  }

  /**
   * The function sets the target position for a motor using Motion Magic control mode.
   *
   * @param theta The `theta` parameter represents the target number of degrees that you want the
   *     motor at the specified index to move to. This method sets the motor target position using
   *     Motion Magic control mode with the specified number of rotations converted using
   *     MustangMath.
   * @param index The `index` parameter is an integer value that represents the index of the motor
   *     in the list of motors. It is used to identify which motor you want to set the target for in
   *     the `motors` list.
   */
  protected void setMotorTargetDegrees(double theta, int index) {
    motors
        .get(index)
        .setControl(
            new MotionMagicVoltage(0)
                .withPosition(MustangMath.getRotationsFromDegrees(gearRatio, theta)));
  }

  /**
   * The setMotorTargetDegrees method sets the target rotations for a motor after converting the
   * passed angle to rotations
   *
   * @param theta the target postion for the motor in roations defaults to the motor at index 0 the
   *     first motor in `registerMotors()`
   */
  protected void setMotorTargetDegrees(double theta) {
    setMotorTargetDegrees(theta, 0);
  }
}

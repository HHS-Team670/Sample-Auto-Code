package frc.team670.libs.Utilities;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TalonFXUtils {

  /**
   * The `holdMotor` function sets a TalonFX motor to maintain its current position using Motion
   * Magic control mode.
   *
   * @param motor The parameter `motor` is of type `TalonFX` motor, The `holdMotor` method takes a
   *     `TalonFX` motor as input and sets its control mode to Motion Magic with a target position
   *     of its current position resulting in it halting any movement
   */
  public static void holdMotor(TalonFX motor) {
    motor.setControl(
        new MotionMagicVoltage(0).withPosition(motor.getPosition().getValueAsDouble()));
  }

  /**
   * The function `getConfig` returns the configuration settings of a TalonFX motor.
   *
   * @param motor The `motor` parameter is of type `TalonFX`, which is a class representing a Talon
   *     FX motor controller in the CTRE Phoenix library for controlling motors in robotics
   *     applications.
   * @return The `getConfig` method returns a `TalonFXConfiguration` object that contains the
   *     configuration settings for the TalonFX motor passed as a parameter.
   */
  public static TalonFXConfiguration getConfig(TalonFX motor) {
    TalonFXConfiguration config = new TalonFXConfiguration();
    motor.getConfigurator().refresh(config);
    return config;
  }

  /**
   * The function `applyConfig` attempts to apply a configuration to a TalonFX motor with retries
   * and logs an error message if unsuccessful.
   *
   * @param motor The `motor` parameter is of type `TalonFX`, which represents a Talon FX motor
   *     controller. It is the motor to which the configuration settings will be applied.
   * @param config The `config` parameter in the `applyConfig` method is of type
   *     `TalonFXConfiguration`. It is used to provide configuration settings for a TalonFX motor,
   *     such as PID constants, sensor phase, sensor type, and other motor settings. This
   *     configuration is applied to the TalonFX
   */
  public static void applyConfig(TalonFX motor, TalonFXConfiguration config) {
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 30; ++i) {
      status = motor.getConfigurator().apply(config);
      if (status.isOK()) {
        break;
      }
    }
    if (!status.isOK()) {
      ConsoleLogger.consoleError(
          "Motor configuration failed: " + status + "\n Motor ID:" + motor.getDeviceID());
    }
  }

  /**
   * The function `isHealthy` checks if a TalonFX motor is not null and is not errored out.
   *
   * @param motor A TalonFX motor object
   * @return The method `isHealthy` is returning a boolean value, which indicates whether the
   *     TalonFX motor is healthy or not. The return value is based on two conditions: 1. The motor
   *     is not null. 2. The motor is not errored.
   */
  public static boolean isHealthy(TalonFX motor) {
    return (motor != null && motor.isAlive());
  }

  /**
   * This Java function constructs a TalonFX motor with a specified motor ID and configuration,
   * applying the configuration settings and setting the neutral mode to Brake.
   *
   * @param motorID The `motorID` parameter in the `construct` method is an integer value that
   *     represents the unique identifier or address of the TalonFX motor being constructed. This ID
   *     is used to identify and communicate with the specific motor within the system.
   * @param config The `config` parameter in the `construct` method is of type
   *     `TalonFXConfiguration`. This parameter is used to configure the settings of the TalonFX
   *     motor controller before returning the constructed `TalonFX` object. The method applies the
   *     configuration settings to the motor using a loop that tries
   * @return The method is returning the TalonFX made.
   */
  public static TalonFX construct(int motorID, TalonFXConfiguration config) {
    TalonFX motor = new TalonFX(motorID);
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 30; ++i) {
      status = motor.getConfigurator().apply(config);
      if (status.isOK()) {
        break;
      }
    }
    if (!status.isOK()) {
      ConsoleLogger.consoleError(
          "Motor configuration failed: " + status + "\n Motor ID:" + motorID);
    }

    motor.setNeutralMode(NeutralModeValue.Brake);
    return motor;
  }
}

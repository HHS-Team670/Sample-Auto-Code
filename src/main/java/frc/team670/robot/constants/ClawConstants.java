package frc.team670.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;

public class ClawConstants {
  public static TalonFXConfiguration motorConfig = new TalonFXConfiguration();

  public static final double kEjectingSpeed = -1; // -0.7;
  public static final int kMotorID = 30;
  public static final double kIdleSpeed = 0.05;
  public static final double kRollingSpeed = 0.7; // 0.3;
  public static final double kEjectTime = 1.5;
  public static final double kEjectTimeAuto = 0.55;

  public static final double kCurrentMax = 60;

  static {
    motorConfig.CurrentLimits.StatorCurrentLimit = kCurrentMax;
    motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
  }
}

package frc.team670.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class AlgaeManipulatorConstants {
  public static TalonFXConfiguration motorConfig = new TalonFXConfiguration();

  public static final double kGearRatio1 = 44.0 / 16;
  public static final double kGearRatio2 = 30.0 / 18;
  public static final NeutralModeValue kIdleMode = NeutralModeValue.Brake;
  public static final double kEjectingSpeed = -1;
  public static final int kMotorID = 31;
  public static final double kIdleSpeed = 0.07;
  public static final double kRollingSpeed = 0.67;
  public static final double kEjectTime = 0.6;
  public static final double kCurrentMax = 40;

  static {
    motorConfig.CurrentLimits.StatorCurrentLimit = kCurrentMax;
    motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
    motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
  }
}

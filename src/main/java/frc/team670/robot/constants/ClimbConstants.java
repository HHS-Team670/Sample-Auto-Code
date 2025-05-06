package frc.team670.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ClimbConstants {
  public static TalonFXConfiguration upConfig = new TalonFXConfiguration();
  public static TalonFXConfiguration downConfig = new TalonFXConfiguration();

  public static final double GEAR_RATIO = 375;
  public static final int MOTOR_ID = 35;
  public static final double P = 4;
  public static final double I = 0;
  public static final double D = 0;
  public static final double kS = 0;
  public static final double kA = 0.01;
  public static final double kV = 0.12;
  public static final double MAX_ROTATOR_RPM = 3600;
  public static final double MAX_ACCELERATION = MAX_ROTATOR_RPM * 2;
  public static final double MotionMagicJerk = 0;

  static {
    upConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive; // A

    // Configure PID gains for Motion Magic®
    upConfig.Slot0.kP = ClimbConstants.P; // Example value, tune as needed
    upConfig.Slot0.kI = ClimbConstants.I;
    upConfig.Slot0.kD = ClimbConstants.D;

    upConfig.Slot0.kS = ClimbConstants.kS;
    upConfig.Slot0.kA = ClimbConstants.kA;
    upConfig.Slot0.kV = ClimbConstants.kV;

    upConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
    upConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;

    upConfig.MotionMagic.MotionMagicCruiseVelocity = 2000.0 / 60.0; // Units:
    // rotations/sec
    upConfig.MotionMagic.MotionMagicAcceleration = ClimbConstants.MAX_ACCELERATION / 60; // Units:
    // rotations/sec^2
    upConfig.MotionMagic.MotionMagicJerk = ClimbConstants.MotionMagicJerk; // Units: rotations/sec^3
    upConfig.CurrentLimits.StatorCurrentLimit = 100;
    upConfig.CurrentLimits.StatorCurrentLimitEnable = true;

    upConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    downConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive; // A

    // Configure PID gains for Motion Magic®
    downConfig.Slot0.kP = ClimbConstants.P; // Example value, tune as needed
    downConfig.Slot0.kI = ClimbConstants.I;
    downConfig.Slot0.kD = ClimbConstants.D;

    downConfig.Slot0.kS = ClimbConstants.kS;
    downConfig.Slot0.kA = ClimbConstants.kA;
    downConfig.Slot0.kV = ClimbConstants.kV;

    downConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
    downConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;

    downConfig.MotionMagic.MotionMagicCruiseVelocity =
        ClimbConstants.MAX_ROTATOR_RPM / 60; // Units:
    // rotations/sec
    downConfig.MotionMagic.MotionMagicAcceleration = ClimbConstants.MAX_ACCELERATION / 60; // Units:
    // rotations/sec^2
    downConfig.MotionMagic.MotionMagicJerk =
        ClimbConstants.MotionMagicJerk; // Units: rotations/sec^3
    downConfig.CurrentLimits.StatorCurrentLimit = 100;
    downConfig.CurrentLimits.StatorCurrentLimitEnable = true;

    downConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
  }
}

package frc.team670.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TilterConstants {
    public static TalonFXConfiguration motorConfig = new TalonFXConfiguration();

    public static final int kAbsoluteEncoderID = 8;
    public static final int kTilterMotorID = 26;

    public static final double kP = 5;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0.5;
    public static final double kA = 0.01;
    public static final double kV = 0.15;
    public static final double kG = 0;

    public static final double MotionMagicCruiseVelocity = 2000;
    public static final double MotionMagicAcceleration = MotionMagicCruiseVelocity * 4;
    public static final double MotionMagicJerk = 0;
    public static final double kAbsoluteEncoderOffset = 0; // zeroed upward

    public static final double kGearRatio = 12;

    public static final double kMaxOutput = 1;
    public static final double kMinOutput = -1;
    public static final int kContinuousCurrent = 30;
    public static final int kPeakCurrent = 60;

    public static final double kMaxRotatorRPM = 1500;
    public static final double kMinRotatorRPM = 0;
    public static final double kMaxAcceleration = kMaxRotatorRPM * 2.5;

    public static final double kAllowedErrorDegrees = 3;
    public static final double kAllowedErrorRotations = kGearRatio * kAllowedErrorDegrees / 360;

    public static final double kMaxAngle = 105;
    public static final double kMinAngle = -180;
    public static final boolean kIsInverted = false;
    public static final float[] kSoftLimits = {
            (float) (kMaxAngle / 360 * kGearRatio), (float) (kMinAngle / 360 * kGearRatio)
    };

    static {
        motorConfig.Slot0.kP = kP;
        motorConfig.Slot0.kI = kI;
        motorConfig.Slot0.kD = kD;
        motorConfig.Slot0.kS = kS;
        motorConfig.Slot0.kA = kA;
        motorConfig.Slot0.kV = kV;
        motorConfig.Slot0.kG = kG;
        motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        motorConfig.CurrentLimits.StatorCurrentLimit = kPeakCurrent;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;

        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        motorConfig.MotionMagic.MotionMagicCruiseVelocity = MotionMagicCruiseVelocity / 60.; // Units:
        // rotations/sec
        motorConfig.MotionMagic.MotionMagicAcceleration = MotionMagicAcceleration / 60.0; // Units:
        // rotations/sec^2
        motorConfig.MotionMagic.MotionMagicJerk = MotionMagicJerk;
    }
}
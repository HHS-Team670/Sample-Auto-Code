package frc.team670.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;

public class ArmConstants {

    public static TalonFXConfiguration motorConfig = new TalonFXConfiguration();

    public static final int kAbsoluteEncoderID = 1;
    public static final int kMotorID = 25;
    public static final boolean kIsInverted = false;

    public static final double kP = 0.6;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0.225;
    public static final double kA = 0.01; // 3.6363636364 this is average acc 12/3.3, 0.08 is max 12/150
    public static final double kV = 0.08;
    public static final double kG = 0.175;

    public static final double MotionMagicCruiseVelocity = 5500;
    public static final double MotionMagicAcceleration = MotionMagicCruiseVelocity * 7;
    public static final double MotionMagicJerk = 0;

    public static final double kAbsoluteEncoderOffset = 0; // zeroed upward

    public static final double kGearRatio = (12 * (52.0 / 18) * (56.0 / 18));

    public static final double kMaxOutput = 1;
    public static final double kMinOutput = -1;
    public static final int kContinuousCurrent = 20;
    public static final int kPeakCurrent = 40;

    public static final double kMaxRotatorRPM = 2320;
    public static final double kMinRotatorRPM = 0;
    public static final double kMaxAcceleration = kMaxRotatorRPM * 2;

    public static final double kLength = Units.inchesToMeters(16);

    public static final double kAllowedErrorDegrees = 0.8;
    public static final double kAllowedErrorRotations = kGearRatio * kAllowedErrorDegrees / 360;

    public static final double kMaxAngle = 285;
    public static final double kMinAngle = -90;

    public static final float[] kSoftLimits = {
            (float) (kMaxAngle / 360 * kGearRatio), (float) (kMinAngle / 360 * kGearRatio)
    };

    static {

        motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        motorConfig.Slot0.kP = ArmConstants.kP;
        motorConfig.Slot0.kI = ArmConstants.kI;
        motorConfig.Slot0.kD = ArmConstants.kD;
        motorConfig.Slot0.kS = ArmConstants.kS;
        motorConfig.Slot0.kA = ArmConstants.kA;
        motorConfig.Slot0.kV = ArmConstants.kV;
        motorConfig.Slot0.kG = ArmConstants.kG;

        motorConfig.CurrentLimits.StatorCurrentLimit = ArmConstants.kPeakCurrent;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;

        // motorConfig.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
        // motorConfig.Feedback.SensorToMechanismRatio = ArmConstants.kGearRatio;

        motorConfig.MotionMagic.MotionMagicCruiseVelocity = ArmConstants.MotionMagicCruiseVelocity / 60; // Units:
        // rotations/sec
        motorConfig.MotionMagic.MotionMagicAcceleration = ArmConstants.MotionMagicAcceleration / 60; // Units:
        // rotations/sec^2
        motorConfig.MotionMagic.MotionMagicJerk = ArmConstants.MotionMagicJerk;

        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    }
}
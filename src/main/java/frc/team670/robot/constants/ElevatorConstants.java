package frc.team670.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;

public class ElevatorConstants {

        public static final double kGearRatio = 12;

        public static TalonFXConfiguration leadMotorConfiguration = new TalonFXConfiguration();
        public static TalonFXConfiguration followerMotorConfiguration = new TalonFXConfiguration();

        public static final int leadMotorID = 20; // leader
        public static final int followerMotorID = 21; // follower
        public static final double ELEVATOR_ALLOWED_ERROR_IN_METERS = 0.07;
        public static final double kP = 1.2;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kS = 0.036;
        public static final double kA = 0.01;
        public static final double kV = 0.12;
        public static final double kG = 0.18;

        public static final double MotionMagicCruiseVelocity = 5500;
        public static final double MotionMagicAcceleration = 20 * MotionMagicCruiseVelocity;
        public static final double MotionMagicJerk = 0;

        public static final double kSimOffset = 0.97;

        public static final double kMinOutput = -1;
        public static final double kMaxOutput = 1;
        private static final double kElevatorSprocketRadius = 0.057; // METERS
        public static final double kCircumferenceSprocket = 2 * Math.PI * kElevatorSprocketRadius; // .28036
        public static final double kMaxExtention = 1.787445; // METERS
        public static final double kMIN_ROTATOR_RPM = 0;
        public static final double kMAX_ROTATOR_RPM = 4500;
        // public static final double ALLOWED_DEVIATION = 0.0;
        public static final int kPeakCurrent = 60;
        public static final int kStatorCurrentLimit = 40;
        public static final boolean kInvertFollower = true;

        public static final float kMaxRotations = (float) ((((kMaxExtention - 0.01) / kCircumferenceSprocket)
                        * kGearRatio));;
        public static final float kMinRotations = 0.0f;
        public static final float[] kSoftLimits = { kMaxRotations, kMinRotations };

        public static final double kMaxHeight = kMaxExtention - 0.01;

        static {

                // LEADER CONFIG

                leadMotorConfiguration.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

                // Configure PID gains for Motion Magic®
                leadMotorConfiguration.Slot0.kP = ElevatorConstants.kP; // Example value, tune as needed
                leadMotorConfiguration.Slot0.kI = ElevatorConstants.kI;
                leadMotorConfiguration.Slot0.kD = ElevatorConstants.kD;

                leadMotorConfiguration.Slot0.kS = ElevatorConstants.kS;
                leadMotorConfiguration.Slot0.kA = ElevatorConstants.kA;
                leadMotorConfiguration.Slot0.kV = ElevatorConstants.kV;

                leadMotorConfiguration.Slot0.kG = ElevatorConstants.kG;

                leadMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
                leadMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
                // Configure Motion Magic® parameters
                leadMotorConfiguration.MotionMagic.MotionMagicCruiseVelocity = ElevatorConstants.MotionMagicCruiseVelocity
                                / 60.; // Units:
                // rotations/sec
                leadMotorConfiguration.MotionMagic.MotionMagicAcceleration = ElevatorConstants.MotionMagicAcceleration
                                / 60.; // Units:
                // rotations/sec^2
                leadMotorConfiguration.MotionMagic.MotionMagicJerk = ElevatorConstants.MotionMagicJerk; // Units:
                // rotations/sec^3
                leadMotorConfiguration.CurrentLimits.StatorCurrentLimit = ElevatorConstants.kStatorCurrentLimit;
                leadMotorConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;

                // FOLLOWER CONFIG

                followerMotorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

                // Configure PID gains for Motion Magic®
                followerMotorConfiguration.Slot0.kP = ElevatorConstants.kP; // Example value, tune as needed
                followerMotorConfiguration.Slot0.kI = ElevatorConstants.kI;
                followerMotorConfiguration.Slot0.kD = ElevatorConstants.kD;

                followerMotorConfiguration.Slot0.kS = ElevatorConstants.kS;
                followerMotorConfiguration.Slot0.kA = ElevatorConstants.kA;
                followerMotorConfiguration.Slot0.kV = ElevatorConstants.kV;

                followerMotorConfiguration.Slot0.kG = ElevatorConstants.kG;

                followerMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
                followerMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
                // Configure Motion Magic® parameters
                followerMotorConfiguration.MotionMagic.MotionMagicCruiseVelocity = ElevatorConstants.MotionMagicCruiseVelocity
                                / 60.; // Units:
                // rotations/sec
                followerMotorConfiguration.MotionMagic.MotionMagicAcceleration = ElevatorConstants.MotionMagicAcceleration
                                / 60.; // Units:
                // rotations/sec^2
                followerMotorConfiguration.MotionMagic.MotionMagicJerk = ElevatorConstants.MotionMagicJerk; // Units:
                // rotations/sec^3
                followerMotorConfiguration.CurrentLimits.StatorCurrentLimit = ElevatorConstants.kStatorCurrentLimit;
                followerMotorConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;
        }
}

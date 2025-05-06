package frc.team670.robot.constants;

import java.util.List;
import java.util.Set;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class VisionConstants {
    public static final String[] kVisionCameraIDs = { "ArducamL", "ArducamR" };
    public static final Transform3d[] kCameraOffsets = {
            // ArducamR - RIGHT
            new Transform3d(
                    new Translation3d(
                            Units.inchesToMeters(10), Units.inchesToMeters(6.5),
                            Units.inchesToMeters(11.7)),
                    new Rotation3d()),
            // ArducamL - LEFT
            new Transform3d(
                    new Translation3d(
                            Units.inchesToMeters(10), -Units.inchesToMeters(6.5),
                            Units.inchesToMeters(11.7)),
                    new Rotation3d())
    };

    public static final double kLockedOnErrorX = 0.3;
    public static final double xLockedOnErrorY = 0.3;
    public static final double kLockedOnErrorDegrees = 10;

    public static final double kPoseAmbiguityCutOff = 0.05;
    public static final List<Set<Integer>> kPossibleFrameFIDCombos = List.of(Set.of(1, 2, 3, 4),
            Set.of(5, 6, 7, 8));

    public static final int kMaxFrameFIDs = 4;

}

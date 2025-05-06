package frc.team670.robot.constants;

import edu.wpi.first.math.util.Units;
import java.util.Map;

public class AprilTagConstants {
  public static int[] redReefTags = new int[] {6, 7, 8, 9, 10, 11};
  public static int[] redStationTags = new int[] {1, 2};
  public static int[] redProcessorTags = new int[] {3};

  public static int[] blueReefTags = new int[] {17, 18, 19, 20, 21, 22};
  public static int[] blueStationTags = new int[] {12, 13};
  public static int[] blueProcessorTags = new int[] {16};

  public static int[] allAprilTags =
      new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};

  // Inches
  public static Map<Integer, Map<String, Double>> aprilTagLocations =
      Map.ofEntries(
          Map.entry(1, Map.of("X", 657.37, "Y", 25.80, "ROT", 126.0, "desiredROT", 144.0)),
          Map.entry(2, Map.of("X", 657.37, "Y", 291.20, "ROT", 234.0, "desiredROT", 36.0)),
          Map.entry(3, Map.of("X", 455.15, "Y", 317.15, "ROT", 270.0, "desiredROT", 0.0)),
          Map.entry(4, Map.of("X", 365.20, "Y", 241.64, "ROT", 0.0, "desiredROT", -90.0)),
          Map.entry(5, Map.of("X", 365.20, "Y", 75.39, "ROT", 0.0, "desiredROT", -90.0)),
          Map.entry(6, Map.of("X", 530.49, "Y", 130.17, "ROT", 300.0, "desiredROT", -30.0)),
          Map.entry(7, Map.of("X", 546.87, "Y", 158.50, "ROT", 0.0, "desiredROT", -90.0)),
          Map.entry(8, Map.of("X", 530.49, "Y", 186.83, "ROT", 60.0, "desiredROT", -150.0)),
          Map.entry(9, Map.of("X", 497.77, "Y", 186.83, "ROT", 120.0, "desiredROT", 150.0)),
          Map.entry(10, Map.of("X", 481.39, "Y", 158.50, "ROT", 180.0, "desiredROT", 90.0)),
          Map.entry(11, Map.of("X", 497.77, "Y", 130.17, "ROT", 240.0, "desiredROT", 30.0)),
          Map.entry(12, Map.of("X", 33.51, "Y", 25.80, "ROT", 54.0, "desiredROT", -144.0)),
          Map.entry(13, Map.of("X", 33.51, "Y", 291.20, "ROT", 306.0, "desiredROT", -36.0)),
          Map.entry(14, Map.of("X", 325.68, "Y", 241.64, "ROT", 180.0, "desiredROT", 90.0)),
          Map.entry(15, Map.of("X", 325.68, "Y", 75.39, "ROT", 180.0, "desiredROT", 90.0)),
          Map.entry(16, Map.of("X", 235.73, "Y", -0.15, "ROT", 90.0, "desiredROT", 0.0)),
          Map.entry(17, Map.of("X", 160.39, "Y", 130.17, "ROT", 240.0, "desiredROT", 30.0)),
          Map.entry(18, Map.of("X", 144.00, "Y", 158.50, "ROT", 180.0, "desiredROT", 90.0)),
          Map.entry(19, Map.of("X", 160.39, "Y", 186.83, "ROT", 120.0, "desiredROT", 150.0)),
          Map.entry(20, Map.of("X", 193.10, "Y", 186.83, "ROT", 60.0, "desiredROT", -150.0)),
          Map.entry(21, Map.of("X", 209.49, "Y", 158.50, "ROT", 0.0, "desiredROT", -90.0)),
          Map.entry(22, Map.of("X", 193.10, "Y", 130.17, "ROT", 300.0, "desiredROT", -30.0)));

  private static double driveBaseRadiusAndOffset =
      Units.metersToInches(DrivetrainConstants.driveBaseRadius + 0.05);
  private static double sideOffsetApriltagToCoral = 6.5;

  private static double xAxisMovement = driveBaseRadiusAndOffset / 2;
  private static double yAxisMovement = driveBaseRadiusAndOffset * Math.sqrt(3) / 2;

  private static double xAxisMovementForSideOffset = sideOffsetApriltagToCoral * Math.sqrt(3) / 2;
  private static double yAxisMovementForSideOffset = sideOffsetApriltagToCoral / 2;

  public static Map<Integer, Map<String, Double>> alignToReefLeft =
      Map.ofEntries(
          Map.entry(
              6,
              Map.of(
                  "X",
                  530.49 + xAxisMovement - xAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  300.0,
                  "desiredROT",
                  -30.0)),
          Map.entry(
              7,
              Map.of(
                  "X",
                  546.87 + driveBaseRadiusAndOffset,
                  "Y",
                  158.50 - sideOffsetApriltagToCoral,
                  "ROT",
                  0.0,
                  "desiredROT",
                  -90.0)),
          Map.entry(
              8,
              Map.of(
                  "X",
                  530.49 + xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  60.0,
                  "desiredROT",
                  -150.0)),
          Map.entry(
              9,
              Map.of(
                  "X",
                  497.77 - xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement + yAxisMovementForSideOffset,
                  "ROT",
                  120.0,
                  "desiredROT",
                  150.0)),
          Map.entry(
              10,
              Map.of(
                  "X",
                  481.39 - driveBaseRadiusAndOffset,
                  "Y",
                  158.50 + sideOffsetApriltagToCoral,
                  "ROT",
                  180.0,
                  "desiredROT",
                  90.0)),
          Map.entry(
              11,
              Map.of(
                  "X",
                  497.77 - xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  240.0,
                  "desiredROT",
                  30.0)),
          Map.entry(
              17,
              Map.of(
                  "X",
                  160.39 - xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  240.0,
                  "desiredROT",
                  30.0)),
          Map.entry(
              18,
              Map.of(
                  "X",
                  144.00 - driveBaseRadiusAndOffset,
                  "Y",
                  158.50 + sideOffsetApriltagToCoral,
                  "ROT",
                  180.0,
                  "desiredROT",
                  90.0)),
          Map.entry(
              19,
              Map.of(
                  "X",
                  160.39 - xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement + yAxisMovementForSideOffset,
                  "ROT",
                  120.0,
                  "desiredROT",
                  150.0)),
          Map.entry(
              20,
              Map.of(
                  "X",
                  193.10 + xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  60.0,
                  "desiredROT",
                  -150.0)),
          Map.entry(
              21,
              Map.of(
                  "X",
                  209.49 + driveBaseRadiusAndOffset,
                  "Y",
                  158.50 - sideOffsetApriltagToCoral,
                  "ROT",
                  0.0,
                  "desiredROT",
                  -90.0)),
          Map.entry(
              22,
              Map.of(
                  "X",
                  193.10 + xAxisMovement - yAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  300.0,
                  "desiredROT",
                  -30.0)));

  public static Map<Integer, Map<String, Double>> alignToReefRight =
      Map.ofEntries(
          Map.entry(
              6,
              Map.of(
                  "X",
                  530.49 + xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement + yAxisMovementForSideOffset,
                  "ROT",
                  300.0,
                  "desiredROT",
                  -30.0)),
          Map.entry(
              7,
              Map.of(
                  "X",
                  546.87 + driveBaseRadiusAndOffset,
                  "Y",
                  158.50 + sideOffsetApriltagToCoral,
                  "ROT",
                  0.0,
                  "desiredROT",
                  -90.0)),
          Map.entry(
              8,
              Map.of(
                  "X",
                  530.49 + xAxisMovement - xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement + yAxisMovementForSideOffset,
                  "ROT",
                  60.0,
                  "desiredROT",
                  -150.0)),
          Map.entry(
              9,
              Map.of(
                  "X",
                  497.77 - xAxisMovement - xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  120.0,
                  "desiredROT",
                  150.0)),
          Map.entry(
              10,
              Map.of(
                  "X",
                  481.39 - driveBaseRadiusAndOffset,
                  "Y",
                  158.50 - sideOffsetApriltagToCoral,
                  "ROT",
                  180.0,
                  "desiredROT",
                  90.0)),
          Map.entry(
              11,
              Map.of(
                  "X",
                  497.77 - xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  240.0,
                  "desiredROT",
                  30.0)),
          Map.entry(
              17,
              Map.of(
                  "X",
                  160.39 - xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  240.0,
                  "desiredROT",
                  30.0)),
          Map.entry(
              18,
              Map.of(
                  "X",
                  144.00 - driveBaseRadiusAndOffset,
                  "Y",
                  158.50 - sideOffsetApriltagToCoral,
                  "ROT",
                  180.0,
                  "desiredROT",
                  90.0)),
          Map.entry(
              19,
              Map.of(
                  "X",
                  160.39 - xAxisMovement - xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement - yAxisMovementForSideOffset,
                  "ROT",
                  120.0,
                  "desiredROT",
                  150.0)),
          Map.entry(
              20,
              Map.of(
                  "X",
                  193.10 + xAxisMovement - xAxisMovementForSideOffset,
                  "Y",
                  186.83 + yAxisMovement + yAxisMovementForSideOffset,
                  "ROT",
                  60.0,
                  "desiredROT",
                  -150.0)),
          Map.entry(
              21,
              Map.of(
                  "X",
                  209.49 + driveBaseRadiusAndOffset,
                  "Y",
                  158.50 + sideOffsetApriltagToCoral,
                  "ROT",
                  0.0,
                  "desiredROT",
                  -90.0)),
          Map.entry(
              22,
              Map.of(
                  "X",
                  193.10 + xAxisMovement + xAxisMovementForSideOffset,
                  "Y",
                  130.17 - yAxisMovement + yAxisMovementForSideOffset,
                  "ROT",
                  300.0,
                  "desiredROT",
                  -30.0)));
}

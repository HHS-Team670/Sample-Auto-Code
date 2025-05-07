package frc.team670.robot.Auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.libs.Auto.AutoPath;
import frc.team670.libs.Auto.ChoreoCommand;
import frc.team670.robot.commands.claw.CoralIntake;
import frc.team670.robot.commands.claw.StartClawEject;
import frc.team670.robot.commands.vision.AlignToClosestAprilTag.CAMERA_SIDE;
import frc.team670.robot.commands.vision.PrepareShootCoral;
import frc.team670.robot.constants.RobotPosition;
import java.util.HashMap;
import java.util.Map;

public class Autos {

  private static final Map<String, AutoPath> autos = new HashMap<>();
  private static final SendableChooser<AutoPath> autoChooser = new SendableChooser<>();

  private static Command scoreCoral(String choreoPath, CAMERA_SIDE cameraSide) {
    return new SequentialCommandGroup(
        new ChoreoCommand(choreoPath),
        new PrepareShootCoral(RobotPosition.L4, cameraSide),
        new StartClawEject());
  }

  private static Command intakeCoral(String choreoPath) {
    return new ParallelCommandGroup(new ChoreoCommand(choreoPath), new CoralIntake());
  }

  public static void addAuto(String name, AutoPath path) {
    autos.put(name, path);
  }

  public static Command getSelected() {
    return autoChooser.getSelected().compileAuto();
  }

  public static Command getNamed(String name) {
    return autos.get(name).compileAuto();
  }

  static {
    AutoPath center = new AutoPath("center");
    center.addCommands(scoreCoral("C, 1R", CAMERA_SIDE.RIGHT));

    AutoPath left = new AutoPath("left");
    left.addCommands(
        scoreCoral("L, 5L", CAMERA_SIDE.LEFT),
        intakeCoral("5L, SL"),
        scoreCoral("SL, 5R", CAMERA_SIDE.RIGHT),
        intakeCoral("5R, SL"),
        scoreCoral("SL, 4L", CAMERA_SIDE.LEFT));

    AutoPath right = new AutoPath("right");
    right.addCommands(
        scoreCoral("R, 3R", CAMERA_SIDE.RIGHT),
        intakeCoral("3R, SR"),
        scoreCoral("SR, 3L", CAMERA_SIDE.LEFT),
        intakeCoral("3L, SR"),
        scoreCoral("SR, 4R", CAMERA_SIDE.RIGHT));

    autoChooser.setDefaultOption("center", center);
    autos.forEach(
        (name, path) -> {
          autoChooser.addOption(name, path);
        });
  }
}

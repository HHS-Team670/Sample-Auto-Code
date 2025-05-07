package frc.team670.robot.Auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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

  static {
    AutoPath center = new AutoPath("center");
    center.addCommands(
        new ChoreoCommand("C, 1R"),
        new PrepareShootCoral(RobotPosition.L4, CAMERA_SIDE.RIGHT),
        new StartClawEject());

    AutoPath left = new AutoPath("left");
    left.addCommands(
        new ChoreoCommand("L, 5L"),
        new PrepareShootCoral(RobotPosition.L4, CAMERA_SIDE.LEFT),
        new StartClawEject(),
        new ParallelCommandGroup(new ChoreoCommand("5L, SL"), new CoralIntake()),
        new ChoreoCommand("SL, 5R"),
        new PrepareShootCoral(RobotPosition.L4, CAMERA_SIDE.RIGHT),
        new StartClawEject(),
        new ParallelCommandGroup(new ChoreoCommand("5R, SL"), new CoralIntake()),
        new ChoreoCommand("SL, 4L"),
        new PrepareShootCoral(RobotPosition.L4, CAMERA_SIDE.LEFT),
        new StartClawEject());

    AutoPath right = new AutoPath("right");
    right.addCommands(
        new ChoreoCommand("R, 3R"),
        new PrepareShootCoral(RobotPosition.L4, CAMERA_SIDE.RIGHT),
        new StartClawEject(),
        new ParallelCommandGroup(new ChoreoCommand("3R, SR"), new CoralIntake()),
        new ChoreoCommand("SR, 3L"),
        new PrepareShootCoral(RobotPosition.L4, CAMERA_SIDE.LEFT),
        new StartClawEject(),
        new ParallelCommandGroup(new ChoreoCommand("3L, SR"), new CoralIntake()),
        new ChoreoCommand("SR, 4R"),
        new PrepareShootCoral(RobotPosition.L4, CAMERA_SIDE.RIGHT),
        new StartClawEject());

    autoChooser.setDefaultOption("center", center);
    autos.forEach(
        (name, path) -> {
          autoChooser.addOption(name, path);
        });
  }

  public static void addAuto(String name, AutoPath path) {
    autos.put(name, path);
  }

  public static Command get(String name) {
    return autos.get(name).compileAuto();
  }
}

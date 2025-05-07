package frc.team670.robot.Auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team670.libs.Auto.AutoPath;
import frc.team670.libs.Auto.ChoreoCommand;
import frc.team670.robot.commands.vision.PrepareShootCoral;
import frc.team670.robot.constants.RobotPosition;

import java.util.HashMap;
import java.util.Map;

public class Autos {

  private static final Map<String, AutoPath> autos = new HashMap<>();
  private static final SendableChooser<AutoPath> autoChooser = new SendableChooser<>();

  static {
    AutoPath center = new AutoPath("center");
    center.addCommand(new PrepareShootCoral(RobotPosition.L4));

    AutoPath left = new AutoPath("left");

    left.addCommand(
        new ParallelCommandGroup(
            new ChoreoCommand("L, 5L"),
            new PrepareShootCoral(RobotPosition.L4))

    );

    AutoPath right = new AutoPath("right");

    autoChooser.setDefaultOption(null, null);

    autos.forEach((name, path) -> {
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

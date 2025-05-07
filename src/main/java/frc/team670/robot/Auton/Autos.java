// 2025
package frc.team670.robot.Auton;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.libs.Auto.AutoInstance;
import frc.team670.libs.Auto.MustangAutoBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

public class Autos {

  static Map<String, AutoInstance> autos = new HashMap<>();

  static {
    AutoInstance left = MustangAutoBuilder.createAutoRefrance();
    MustangAutoBuilder.EditAuto(left);

    try {
      PathPlannerPath L_5L = PathPlannerPath.fromChoreoTrajectory("L, 5L");
    } catch (FileVersionException | IOException | ParseException e) {
      e.printStackTrace();
    }

    MustangAutoBuilder.AddParallel();

    autos.put("default", null);
  }

  public static Command get(String name) {
    return autos.get(name).finish();
  }
}

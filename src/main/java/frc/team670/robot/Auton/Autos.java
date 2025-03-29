// 2025
package frc.team670.robot.Auton;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.libs.Auto.AutoInstance;
import java.util.HashMap;
import java.util.Map;

public class Autos {

  Map<String, AutoInstance> autos = new HashMap<>();

  public Autos() {}

  public Command get(String name) {
    return autos.get(name).finish();
  }
}

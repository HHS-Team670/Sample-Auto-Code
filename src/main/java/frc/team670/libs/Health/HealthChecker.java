package frc.team670.libs.Health;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Utilities.ConsoleLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.littletonrobotics.junction.Logger;

public class HealthChecker {

  public static Map<Subsystem, Health> healthStates = new HashMap<>();

  /**
   * The function `getSubsystems` returns a list of subsystems that have been
   * registered
   * 
   * @return A list of Subsystem objects is being returned.
   */
  public static List<Subsystem> getSubsystems() {
    List<Subsystem> subsystems = new ArrayList<>();
    healthStates.forEach(
        (s, h) -> {
          subsystems.add(s);
        });
    return subsystems;
  }

  /**
   * The function `reportHealth` updates the health state of a subsystem if it is
   * registered, otherwise
   * it logs a warning message.
   * 
   * @param subsystem The subsystem that is reporting its health
   * @param state     The `state` parameter in the `reportHealth` method
   *                  represents the health status of a
   *                  the subsystem. It is used to update the health state
   *                  of the subsystem in the list of registerd subsytems
   */
  public static void reportHealth(Subsystem subsystem, Health state) {
    if (healthStates.get(subsystem) == null) {
      ConsoleLogger.consoleWarning(
          subsystem.getName()
              + " Attempted to report health while not registered\n Reported Health: "
              + state);
    } else {
      healthStates.put(subsystem, state);
    }
  }

  /**
   * The `register` function in Java initializes a subsystem's health state as
   * unknown in a map.
   * 
   * @param subsystem The `subsystem` parameter in the `register` method is the
   *                  subsystem to register
   * 
   */
  public static Void register(Subsystem subsystem) {
    healthStates.put(subsystem, Health.UNKNOWN);
    return null;
  }

  /**
   * The `periodic` function iterates through health states of subsystems,
   * unregistering any with a red
   * health status and recording the health status in the logger.
   */
  public static void periodic() {
    healthStates.forEach(
        (Subsystem s, Health h) -> {
          if (h == Health.RED) {
            CommandScheduler.getInstance().unregisterSubsystem(s);
          }
          Logger.recordOutput(s.getName() + "/Health", h);
        });
  }
}

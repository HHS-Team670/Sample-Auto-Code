package frc.team670.libs.Auto;

import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team670.libs.Utilities.TypeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The `MustangAutoBuilder` class in Java provides methods for creating,
 * editing, and managing Autos
 * with choreo and named commands.
 */
public final class MustangAutoBuilder {

  public static List<AutoInstance> autos;
  public static AutoInstance currentEdit;

  public static Map<String, Command> namedCommands = new HashMap<String, Command>();

  /**
   * The function creates a new AutoInstance object, adds it to a list, sets it as
   * the currentEdit
   * object, assigns an index to it, and returns it.
   *
   * @return The method `createAutoRefrance` is returning the `currentEdit`
   *         object, which is an
   *         instance of `AutoInstance`.
   */
  public static AutoInstance createAutoRefrance() {
    autos.add(new AutoInstance());
    currentEdit = autos.get(autos.size() - 1);
    currentEdit.index = autos.size() - 1;
    return currentEdit;
  }

  /**
   * The function `getAuto` returns an `AutoInstance` object at a specified index
   * from a collection
   * of autos.
   *
   * @param index The `index` parameter in the `getAuto` method is used to specify
   *              the position of
   *              the `AutoInstance` object that you want to retrieve from the
   *              `autos` collection. The method
   *              will return the `AutoInstance` object located at the specified
   *              index in the collection.
   * @return An AutoInstance object is being returned.
   */
  public static AutoInstance getAuto(int index) {
    return autos.get(index);
  }

  /**
   * The function `AddFollowChoreo` adds a choreo command to the current edit
   * based on a choreo
   * trajectory.
   *
   * @param choreoName The `AddFollowChoreo` method takes a `choreoName`
   *                   parameter, which is the
   *                   name of the choreography to be added. This method creates a
   *                   `PathPlannerPath` object from
   *                   the choreography trajectory specified by the `choreoName`
   *                   and then adds the command used to
   *                   follow the path to the auto's refrance
   */
  public static void AddFollowChoreo(String choreoName) {
    try {
      PathPlannerPath path = PathPlannerPath.fromChoreoTrajectory(choreoName);
      currentEdit.commands.add(new ChoreoCommand(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * The function `FromName` retrieves a command based on a given name and adds it
   * to the list of
   * commands in the current edit.
   *
   * @param name The `name` parameter is a string that represents the name of a
   *             command.
   */
  public static void FromName(String name) {
    Command command = namedCommands.get(name);
    currentEdit.commands.add(command);
  }

  /**
   * The Define function stores a command with a specified name in a map called
   * namedCommands.
   *
   * @param command The `command` parameter is an object of type `Command`, which
   *                likely represents
   *                a specific action or operation that can be executed.
   * @param name    The `name` parameter is a String that represents the name or
   *                identifier of a
   *                command.
   */
  public static void Define(Command command, String name) {
    namedCommands.put(name, command);
  }

  /**
   * The `AddCommand` function takes in a variable number of `Command` objects and
   * adds them to the
   * `currentEdit` commands list.
   */
  public static void AddCommand(Command... additon) {
    for (Command c : additon) {
      currentEdit.commands.add(c);
    }
  }

  public static AutoInstance mirrorPath(AutoInstance original) {
    return TypeUtils.unimplemented();
  }

  /**
   * The `AddParallel` function takes in a variable number of `Command` objects
   * and
   * adds them to the
   * `currentEdit` commands list. to be run at the same time
   */
  public static void AddParallel(Command... addition) {
    currentEdit.commands.add(new ParallelCommandGroup(addition));
  }

  /**
   * The function `EditAuto` takens in an auto setting it as the current edit if
   * it exists, or
   * adding it to the list if not.
   *
   * @param refrance The `EditAuto` method takes an `AutoInstance` object as a
   *                 parameter named
   *                 `refrance`. This method is used to edit or update an existing
   *                 `AutoInstance` object in a
   *                 list of `autos`. If the `refrance` object is found in the
   *                 list, it sets it as
   */
  public static void EditAuto(AutoInstance refrance) {
    for (AutoInstance a : autos) {
      // this is to check for the same object not equality
      if (a == refrance) {
        currentEdit = a;
        return;
      }
    }
    currentEdit = refrance;
    autos.add(refrance);
    refrance.index = autos.size() - 1;
  }
}

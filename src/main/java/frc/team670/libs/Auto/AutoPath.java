package frc.team670.libs.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.libs.Utilities.TypeUtils;
import frc.team670.robot.Autos;

import java.util.ArrayList;
import java.util.List;

public class AutoPath {
  private List<Command> allCommands = new ArrayList<>();
  private String name;

  public AutoPath(String name) {
    this.name = name;
    Autos.addAuto(this.name, this);
  }

  /**
   * The `compileAuto` function compiles a list of commands into a single command
   * by chaining them
   * together using the `andThen` method.
   * 
   * @return The `compileAuto` method returns a `Command` object that represents
   *         the sequential
   *         composition of all commands stored in the `allCommands` list. The
   *         method iterates through the list
   *         of commands starting from index 0, and then uses the `andThen` method
   *         to combine each command with
   *         the previous one in sequence. The final result is the composition of
   *         all commands in the list,
   *         which is returned
   */
  public Command compileAuto() {
    Command current = allCommands.get(0);
    for (int i = 1; i < allCommands.size(); i++) {
      current = current.andThen(allCommands.get(i));
    }
    return current;
  }

  /**
   * The `addCommands` function adds multiple Command objects to a
   * the list of commands for this auto path
   */
  public void addCommands(Command... commands) {
    for (Command c : commands) {
      allCommands.add(c);
    }
  }

  /**
   * The function `mirrorPath` is unimplemented and will return null
   * by using the TypeUtils.unimplemented logging system
   */
  public AutoPath mirrorPath() {
    return TypeUtils.unimplemented();
  }
}

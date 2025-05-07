package frc.team670.libs.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.libs.Utilities.TypeUtils;
import frc.team670.robot.Auton.Autos;
import java.util.ArrayList;
import java.util.List;

public class AutoPath {
  private List<Command> allCommands = new ArrayList<>();
  private String name;

  public AutoPath(String name) {
    this.name = name;
    Autos.addAuto(this.name, this);
  }

  public Command compileAuto() {
    Command current = allCommands.get(0);
    for (int i = 1; i < allCommands.size(); i++) {
      current = current.andThen(allCommands.get(i));
    }
    return current;
  }

  public void addCommands(Command... commands) {
    for (Command c : commands) {
      allCommands.add(c);
    }
  }

  public AutoPath mirrorPath() {
    return TypeUtils.unimplemented();
  }
}

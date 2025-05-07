package frc.team670.libs.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.libs.Utilities.TypeUtils;
import frc.team670.robot.Auton.Autos;

import java.util.ArrayList;
import java.util.List;

public class AutoPath {
  private List<Command> commands = new ArrayList<>();
  private String name;

  public AutoPath(String name) {
    this.name = name;
    Autos.addAuto(this.name, this);
  }

  public Command compileAuto() {
    Command current = commands.get(0);
    for (int i = 1; i < commands.size(); i++) {
      current = current.andThen(commands.get(i));
    }
    return current;
  }

  public void addCommand(Command... additon) {
    for (Command c : additon) {
      commands.add(c);
    }
  }

  public AutoPath mirrorPath() {
    return TypeUtils.unimplemented();
  }
}

package frc.team670.libs.Auto;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;

public class AutoInstance {
    public List<Command> commands;
    public int index;

    public Command finish(){
        Command current = commands.get(0);
        for (int i = 1; i < commands.size(); i++){
            current = current.andThen(commands.get(i));
        }
        return current;
    }

}

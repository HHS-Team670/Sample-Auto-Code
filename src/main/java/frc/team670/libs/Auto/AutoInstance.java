package frc.team670.libs.Auto;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * The `AutoInstance` class in Java contains a method `finish` that combines a list of commands into a
 * single composite command using the `andThen` method.
 */
public class AutoInstance {
    public List<Command> commands;
    public int index;

    /**
     * The `finish` function in Java combines a list of commands into a single command using the
     * `andThen` method.
     * 
     * @return The `finish` method returns a `Command` object that represents the sequence of commands
     * stored in the `commands` list. It iterates through the list of commands, chaining them together
     * using the `andThen` method to create a single composite command, and then returns this composite
     * command.
     */
    public Command finish(){
        Command current = commands.get(0);
        for (int i = 1; i < commands.size(); i++){
            current = current.andThen(commands.get(i));
        }
        return current;
    }

}

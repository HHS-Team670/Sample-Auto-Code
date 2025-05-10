package frc.team670.libs.IO;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class JoysticIO {

  Trigger io;

  public JoysticIO(Trigger io) {
    this.io = io;
  }

  public JoysticIO(XboxController controller, int button) {
    io = new JoystickButton(controller, button);
  }

  public JoysticIO(BooleanEvent event) {
    io = new Trigger(event);
  }

  public JoysticIO(XboxController controller, double angle) {
    io = new POVButton(controller, (int) angle);
  }

  /**
   * The `onTrue` function in Java takes a `Command` object as a parameter and
   * sets it as the command
   * to be executed when the specified trigger is pressed
   * 
   * @param command The `command` parameter is an object of type `Command`. it is
   *                the command to be run once the trigger is pressed
   * @return The method `JoysticIO` is returning an instance of itself
   * 
   */
  public JoysticIO onTrue(Command command) {
    io.onTrue(command);
    return this;
  }

  /**
   * The `onTrue` function in Java takes a `Runnable` object as a parameter and
   * sets it as the command
   * to be executed when the specified trigger is pressed
   * 
   * @param command The `command` parameter is an object of type `Runnable`. it is
   *                to be executed once the trigger has been pressed
   * @return The method `JoysticIO` is returning an instance of itself
   * 
   */
  public JoysticIO onTrue(Runnable command) {
    io.onTrue(new InstantCommand(command));
    return this;
  }

  /**
   * The `onFalse` function in Java takes a `Command` object as a parameter and
   * sets it as the command
   * to be executed when the specified trigger is no longer being pressed
   * 
   * @param command The `command` parameter is an object of type `Command`. it is
   *                the command to be executed once the trigger is no longer being
   *                pressed
   * @return The method `JoysticIO` is returning an instance of itself
   * 
   */
  public JoysticIO onFalse(Command command) {
    io.onFalse(command);
    return this;
  }

  /**
   * The `onFalse` function in Java takes a `Runnable` object as a parameter and
   * sets it as the command
   * to be executed when the specified trigger is no longer being pressed
   * 
   * @param command The `command` parameter is an object of type `Runnable`. it is
   *                to be executed once the trigger is no longer being
   *                pressed
   * @return The method `JoysticIO` is returning an instance of itself
   * 
   */
  public JoysticIO onFalse(Runnable command) {
    io.onFalse(new InstantCommand(command));
    return this;
  }
}

package frc.team670.libs.IO;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class XboxJoysticButtons {

  public static XboxController driverController;
  public static XboxController operatorController;

  public static ControllerUtils driverUtils;
  public static ControllerUtils operatorUtils;

  public static JoystickButton Driver_ButtonA;
  public static JoystickButton Operator_ButtonA;
  public static JoystickButton Driver_ButtonB;
  public static JoystickButton Operator_ButtonB;
  public static JoystickButton Driver_ButtonX;
  public static JoystickButton Operator_ButtonX;
  public static JoystickButton Driver_ButtonY;
  public static JoystickButton Operator_ButtonY;
  public static JoystickButton Driver_RightBumper;
  public static JoystickButton Operator_RightBumper;
  public static JoystickButton Driver_LeftBumper;
  public static JoystickButton Operator_LeftBumper;
  public static JoystickButton Driver_ButtonBack;
  public static JoystickButton Operator_ButtonBack;
  public static JoystickButton Driver_ButtonStart;
  public static JoystickButton Operator_ButtonStart;
  public static JoystickButton Driver_LeftJoystickPress;
  public static JoystickButton Operator_LeftJoysticPress;
  public static JoystickButton Driver_RightJoystickPress;
  public static JoystickButton Operator_RightJoysticPress;

  public static Trigger Driver_LeftTrigger;
  public static Trigger Driver_RightTrigger;
  public static Trigger Operator_LeftTrigger;
  public static Trigger Operator_RightTrigger;

  public static POVButton Driver_Dpad_North;
  public static POVButton Driver_Dpad_NorthEast;
  public static POVButton Driver_Dpad_NorthWest;
  public static POVButton Driver_Dpad_South;
  public static POVButton Driver_Dpad_SouthEast;
  public static POVButton Driver_Dpad_SouthWest;
  public static POVButton Driver_Dpad_East;
  public static POVButton Driver_Dpad_West;
  public static POVButton Operator_Dpad_North;
  public static POVButton Operator_Dpad_NorthEast;
  public static POVButton Operator_Dpad_NorthWest;
  public static POVButton Operator_Dpad_South;
  public static POVButton Operator_Dpad_SouthEast;
  public static POVButton Operator_Dpad_SouthWest;
  public static POVButton Operator_Dpad_East;
  public static POVButton Operator_Dpad_West;

  /**
   * This function must be called before binding any of the buttons
   *
   * <p>The function `configureJoysticks` sets up various joystick buttons and triggers for both a
   * driver and an operator Xbox controller.
   *
   * @param driver The `driver` parameter in the `configureJoysticks` method is an `XboxController`
   *     object representing the controller used by the driver of the robot. This controller is used
   *     to set up various joystick buttons, triggers, and D-pad buttons for controlling the robot.
   * @param operator The `operator` parameter in the `configureJoysticks` method is an
   *     XboxController object representing the controller used by the operator of the robot. This
   *     controller is used to set up various joystick buttons, triggers, and D-pad buttons for the
   *     operator to interact with the robot.
   */
  public static void configureJoysticks(XboxController driver, XboxController operator) {

    driverController = driver;
    operatorController = operator;

    Driver_ButtonA = new JoystickButton(driver, XboxButtons.A);
    Operator_ButtonA = new JoystickButton(operator, XboxButtons.A);
    Driver_ButtonB = new JoystickButton(driver, XboxButtons.B);
    Operator_ButtonB = new JoystickButton(operator, XboxButtons.B);
    Driver_ButtonX = new JoystickButton(driver, XboxButtons.X);
    Operator_ButtonX = new JoystickButton(operator, XboxButtons.X);
    Driver_ButtonY = new JoystickButton(driver, XboxButtons.Y);
    Operator_ButtonY = new JoystickButton(operator, XboxButtons.Y);
    Driver_LeftBumper = new JoystickButton(driver, XboxButtons.LEFT_BUMPER);
    Operator_LeftBumper = new JoystickButton(operator, XboxButtons.LEFT_BUMPER);
    Driver_RightBumper = new JoystickButton(driver, XboxButtons.RIGHT_BUMPER);
    Operator_RightBumper = new JoystickButton(operator, XboxButtons.RIGHT_BUMPER);
    Driver_ButtonBack = new JoystickButton(driver, XboxButtons.BACK);
    Operator_ButtonBack = new JoystickButton(operator, XboxButtons.BACK);
    Driver_ButtonStart = new JoystickButton(driver, XboxButtons.START);
    Operator_ButtonStart = new JoystickButton(operator, XboxButtons.START);
    Driver_LeftJoystickPress = new JoystickButton(driver, XboxButtons.LEFT_JOYSTICK_BUTTON);
    Operator_LeftJoysticPress = new JoystickButton(operator, XboxButtons.LEFT_JOYSTICK_BUTTON);
    Driver_RightJoystickPress = new JoystickButton(driver, XboxButtons.RIGHT_JOYSTICK_BUTTON);
    Operator_RightJoysticPress = new JoystickButton(operator, XboxButtons.RIGHT_JOYSTICK_BUTTON);

    Driver_LeftTrigger =
        new Trigger(driver.leftTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Driver_RightTrigger =
        new Trigger(driver.rightTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Operator_LeftTrigger =
        new Trigger(operator.leftTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Operator_RightTrigger =
        new Trigger(operator.rightTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));

    Driver_Dpad_North = new POVButton(driver, 0);
    Driver_Dpad_NorthEast = new POVButton(driver, 45);
    Driver_Dpad_East = new POVButton(driver, 90);
    Driver_Dpad_SouthEast = new POVButton(driver, 135);
    Driver_Dpad_South = new POVButton(driver, 180);
    Driver_Dpad_SouthWest = new POVButton(driver, 225);
    Driver_Dpad_West = new POVButton(driver, 270);
    Driver_Dpad_NorthWest = new POVButton(driver, 315);
    Operator_Dpad_North = new POVButton(operator, 0);
    Operator_Dpad_NorthEast = new POVButton(operator, 45);
    Operator_Dpad_East = new POVButton(operator, 90);
    Operator_Dpad_SouthEast = new POVButton(operator, 135);
    Operator_Dpad_South = new POVButton(operator, 180);
    Operator_Dpad_SouthWest = new POVButton(operator, 225);
    Operator_Dpad_West = new POVButton(operator, 270);
    Operator_Dpad_NorthWest = new POVButton(operator, 315);

    driverUtils = new ControllerUtils(driver);
    operatorUtils = new ControllerUtils(operator);
  }

  /**
   * This function must be called before binding any of the buttons
   *
   * <p>The function `configureJoysticks` sets up various buttons and triggers on an Xbox controller
   * for use in a Java program.
   *
   * @param driver The `driver` parameter in the `configureJoysticks` method is an `XboxController`
   *     object that represents the controller being configured. This method sets up various buttons
   *     and triggers on the Xbox controller for use in controlling the robot.
   */
  public static void configureJoysticks(XboxController driver) {

    driverController = driver;

    Driver_ButtonA = new JoystickButton(driver, XboxButtons.A);
    Driver_ButtonB = new JoystickButton(driver, XboxButtons.B);
    Driver_ButtonX = new JoystickButton(driver, XboxButtons.X);
    Driver_ButtonY = new JoystickButton(driver, XboxButtons.Y);
    Driver_LeftBumper = new JoystickButton(driver, XboxButtons.LEFT_BUMPER);
    Driver_RightBumper = new JoystickButton(driver, XboxButtons.RIGHT_BUMPER);
    Driver_ButtonBack = new JoystickButton(driver, XboxButtons.BACK);
    Driver_ButtonStart = new JoystickButton(driver, XboxButtons.START);
    Driver_LeftJoystickPress = new JoystickButton(driver, XboxButtons.LEFT_JOYSTICK_BUTTON);
    Driver_RightJoystickPress = new JoystickButton(driver, XboxButtons.RIGHT_JOYSTICK_BUTTON);

    Driver_LeftTrigger =
        new Trigger(driver.leftTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Driver_RightTrigger =
        new Trigger(driver.rightTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));

    Driver_Dpad_North = new POVButton(driver, 0);
    Driver_Dpad_NorthEast = new POVButton(driver, 45);
    Driver_Dpad_East = new POVButton(driver, 90);
    Driver_Dpad_SouthEast = new POVButton(driver, 135);
    Driver_Dpad_South = new POVButton(driver, 180);
    Driver_Dpad_SouthWest = new POVButton(driver, 225);
    Driver_Dpad_West = new POVButton(driver, 270);
    Driver_Dpad_NorthWest = new POVButton(driver, 315);

    driverUtils = new ControllerUtils(driver);
  }
}

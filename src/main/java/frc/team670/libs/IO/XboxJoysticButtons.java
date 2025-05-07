package frc.team670.libs.IO;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class XboxJoysticButtons {

  public static XboxController driverController;
  public static XboxController operatorController;

  public static ControllerUtils driverUtils;
  public static ControllerUtils operatorUtils;

  public static JoysticIO Driver_ButtonA;
  public static JoysticIO Operator_ButtonA;
  public static JoysticIO Driver_ButtonB;
  public static JoysticIO Operator_ButtonB;
  public static JoysticIO Driver_ButtonX;
  public static JoysticIO Operator_ButtonX;
  public static JoysticIO Driver_ButtonY;
  public static JoysticIO Operator_ButtonY;
  public static JoysticIO Driver_RightBumper;
  public static JoysticIO Operator_RightBumper;
  public static JoysticIO Driver_LeftBumper;
  public static JoysticIO Operator_LeftBumper;
  public static JoysticIO Driver_ButtonBack;
  public static JoysticIO Operator_ButtonBack;
  public static JoysticIO Driver_ButtonStart;
  public static JoysticIO Operator_ButtonStart;
  public static JoysticIO Driver_LeftJoystickPress;
  public static JoysticIO Operator_LeftJoysticPress;
  public static JoysticIO Driver_RightJoystickPress;
  public static JoysticIO Operator_RightJoysticPress;

  public static JoysticIO Driver_LeftTrigger;
  public static JoysticIO Driver_RightTrigger;
  public static JoysticIO Operator_LeftTrigger;
  public static JoysticIO Operator_RightTrigger;

  public static JoysticIO Driver_Dpad_North;
  public static JoysticIO Driver_Dpad_NorthEast;
  public static JoysticIO Driver_Dpad_NorthWest;
  public static JoysticIO Driver_Dpad_South;
  public static JoysticIO Driver_Dpad_SouthEast;
  public static JoysticIO Driver_Dpad_SouthWest;
  public static JoysticIO Driver_Dpad_East;
  public static JoysticIO Driver_Dpad_West;
  public static JoysticIO Operator_Dpad_North;
  public static JoysticIO Operator_Dpad_NorthEast;
  public static JoysticIO Operator_Dpad_NorthWest;
  public static JoysticIO Operator_Dpad_South;
  public static JoysticIO Operator_Dpad_SouthEast;
  public static JoysticIO Operator_Dpad_SouthWest;
  public static JoysticIO Operator_Dpad_East;
  public static JoysticIO Operator_Dpad_West;

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

    Driver_ButtonA = new JoysticIO(driver, XboxButtons.A);
    Operator_ButtonA = new JoysticIO(operator, XboxButtons.A);
    Driver_ButtonB = new JoysticIO(driver, XboxButtons.B);
    Operator_ButtonB = new JoysticIO(operator, XboxButtons.B);
    Driver_ButtonX = new JoysticIO(driver, XboxButtons.X);
    Operator_ButtonX = new JoysticIO(operator, XboxButtons.X);
    Driver_ButtonY = new JoysticIO(driver, XboxButtons.Y);
    Operator_ButtonY = new JoysticIO(operator, XboxButtons.Y);
    Driver_LeftBumper = new JoysticIO(driver, XboxButtons.LEFT_BUMPER);
    Operator_LeftBumper = new JoysticIO(operator, XboxButtons.LEFT_BUMPER);
    Driver_RightBumper = new JoysticIO(driver, XboxButtons.RIGHT_BUMPER);
    Operator_RightBumper = new JoysticIO(operator, XboxButtons.RIGHT_BUMPER);
    Driver_ButtonBack = new JoysticIO(driver, XboxButtons.BACK);
    Operator_ButtonBack = new JoysticIO(operator, XboxButtons.BACK);
    Driver_ButtonStart = new JoysticIO(driver, XboxButtons.START);
    Operator_ButtonStart = new JoysticIO(operator, XboxButtons.START);
    Driver_LeftJoystickPress = new JoysticIO(driver, XboxButtons.LEFT_JOYSTICK_BUTTON);
    Operator_LeftJoysticPress = new JoysticIO(operator, XboxButtons.LEFT_JOYSTICK_BUTTON);
    Driver_RightJoystickPress = new JoysticIO(driver, XboxButtons.RIGHT_JOYSTICK_BUTTON);
    Operator_RightJoysticPress = new JoysticIO(operator, XboxButtons.RIGHT_JOYSTICK_BUTTON);

    Driver_LeftTrigger =
        new JoysticIO(driver.leftTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Driver_RightTrigger =
        new JoysticIO(driver.rightTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Operator_LeftTrigger =
        new JoysticIO(operator.leftTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Operator_RightTrigger =
        new JoysticIO(operator.rightTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));

    Driver_Dpad_North = new JoysticIO(driver, 0.0);
    Driver_Dpad_NorthEast = new JoysticIO(driver, 45.0);
    Driver_Dpad_East = new JoysticIO(driver, 90.0);
    Driver_Dpad_SouthEast = new JoysticIO(driver, 135.0);
    Driver_Dpad_South = new JoysticIO(driver, 180.0);
    Driver_Dpad_SouthWest = new JoysticIO(driver, 225.0);
    Driver_Dpad_West = new JoysticIO(driver, 270.0);
    Driver_Dpad_NorthWest = new JoysticIO(driver, 315.0);
    Operator_Dpad_North = new JoysticIO(operator, 0.0);
    Operator_Dpad_NorthEast = new JoysticIO(operator, 45.0);
    Operator_Dpad_East = new JoysticIO(operator, 90.0);
    Operator_Dpad_SouthEast = new JoysticIO(operator, 135.0);
    Operator_Dpad_South = new JoysticIO(operator, 180.0);
    Operator_Dpad_SouthWest = new JoysticIO(operator, 225.0);
    Operator_Dpad_West = new JoysticIO(operator, 270.0);
    Operator_Dpad_NorthWest = new JoysticIO(operator, 315.0);

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

    Driver_ButtonA = new JoysticIO(driver, XboxButtons.A);
    Driver_ButtonB = new JoysticIO(driver, XboxButtons.B);
    Driver_ButtonX = new JoysticIO(driver, XboxButtons.X);
    Driver_ButtonY = new JoysticIO(driver, XboxButtons.Y);
    Driver_LeftBumper = new JoysticIO(driver, XboxButtons.LEFT_BUMPER);
    Driver_RightBumper = new JoysticIO(driver, XboxButtons.RIGHT_BUMPER);
    Driver_ButtonBack = new JoysticIO(driver, XboxButtons.BACK);
    Driver_ButtonStart = new JoysticIO(driver, XboxButtons.START);
    Driver_LeftJoystickPress = new JoysticIO(driver, XboxButtons.LEFT_JOYSTICK_BUTTON);
    Driver_RightJoystickPress = new JoysticIO(driver, XboxButtons.RIGHT_JOYSTICK_BUTTON);

    Driver_LeftTrigger =
        new JoysticIO(driver.leftTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));
    Driver_RightTrigger =
        new JoysticIO(driver.rightTrigger(CommandScheduler.getInstance().getDefaultButtonLoop()));

    Driver_Dpad_North = new JoysticIO(driver, 0.0);
    Driver_Dpad_NorthEast = new JoysticIO(driver, 45.0);
    Driver_Dpad_East = new JoysticIO(driver, 90.0);
    Driver_Dpad_SouthEast = new JoysticIO(driver, 135.0);
    Driver_Dpad_South = new JoysticIO(driver, 180.0);
    Driver_Dpad_SouthWest = new JoysticIO(driver, 225.0);
    Driver_Dpad_West = new JoysticIO(driver, 270.0);
    Driver_Dpad_NorthWest = new JoysticIO(driver, 315.0);

    driverUtils = new ControllerUtils(driver);
  }
}

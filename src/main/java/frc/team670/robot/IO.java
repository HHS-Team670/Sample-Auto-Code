package frc.team670.robot;

import static frc.team670.libs.IO.XboxJoysticButtons.*;

import edu.wpi.first.wpilibj.XboxController;

public class IO {

  public static XboxController driver = new XboxController(0);
  public static XboxController operator = new XboxController(0);

  public static void configureBindings() {
    // this line must be before anything else
    configureJoysticks(driver, operator);

    // driver controls

    // operator controls

  }
}

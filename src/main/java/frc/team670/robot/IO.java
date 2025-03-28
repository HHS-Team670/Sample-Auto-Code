package frc.team670.robot;

/**
 * All of the buttons you may need to bind are from this static import it also has utils for controlers
 * Called driverUtils, operatorUtils of type {@link ControllerUtils}
 */
import static frc.team670.libs.IO.XboxJoysticButtons.*;

import edu.wpi.first.wpilibj.XboxController;

public class IO {

  public static XboxController driver = new XboxController(0);
  public static XboxController operator = new XboxController(1);

  public static void configureDriverControls(){
    

  }

  public static void configureOperatorControls(){


  }

  public static void configureBindings() {
    // this line must be before anything else
    configureJoysticks(driver, operator);

    // driver controls

    configureDriverControls();

    // operator controls

    configureOperatorControls();


  }


  public static XboxController getOperatorController(){
    return operator;
  }

  public static XboxController getDriverController(){
    return driver;
  }

}

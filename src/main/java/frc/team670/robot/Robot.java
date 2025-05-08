package frc.team670.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.team670.libs.Health.HealthChecker;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class Robot extends LoggedRobot {
  private Command mAutonomousCommand;

  private final RobotContainer mRobotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  public Robot() {
    mRobotContainer = new RobotContainer();

    Logger.recordMetadata("ProjectName", "670-Robot"); // Set a metadata value

    if (isReal()) {
      Logger.addDataReceiver(new WPILOGWriter("/U/logs")); // Log to a USB stick
      Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
    } else {
      String timestamp = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now());
      File logFolder = new File("logs");
      logFolder.mkdirs();

      Logger.addDataReceiver(new WPILOGWriter("logs/" + timestamp + ".wpilog"));
      Logger.addDataReceiver(new NT4Publisher());
    }

    Logger.start();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    HealthChecker.periodic();
    mRobotContainer.robotPeriodic();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    try {
      mAutonomousCommand = mRobotContainer.getAutonomousCommand();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (mAutonomousCommand != null) {
      mAutonomousCommand.schedule();
    }
    mRobotContainer.autonomousInit();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    mRobotContainer.autonomousPeriodic();
  }

  @Override
  public void teleopInit() {
    if (mAutonomousCommand != null) {
      mAutonomousCommand.cancel();
    }
    mRobotContainer.teleopInit();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    mRobotContainer.teleopPeriodic();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    mRobotContainer.teleopInit();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    mRobotContainer.testPeriodic();
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
    mRobotContainer.simulationInit();
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    mRobotContainer.simulationPeriodic();
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team670.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Health.HealthChecker;
import frc.team670.robot.Auton.Autos;
import frc.team670.robot.subsystems.AlgaeManipulator;
import frc.team670.robot.subsystems.Arm;
import frc.team670.robot.subsystems.Claw;
import frc.team670.robot.subsystems.Drivetrain;
import frc.team670.robot.subsystems.Elevator;
import frc.team670.robot.subsystems.Tilter;
import frc.team670.robot.subsystems.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  public RobotContainer() {
    OI.configureBindings();
    registerSubsytems(
        Drivetrain.getInstance(),
        Vision.getInstance(),
        Arm.getInstance(),
        Claw.getInstance(),
        Tilter.getInstance(),
        Elevator.getInstance(),
        AlgaeManipulator.getInstance());
  }

  /** This is to be used within the {@link Robot} class to register subsytems */
  public void registerSubsytems(Subsystem... subsystems) {
    for (Subsystem s : subsystems) {
      HealthChecker.register(s);
    }
    CommandScheduler.getInstance()
        .registerSubsystem(subsystems); // the subsytems periodic is called by the
    // CommandScheduler
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Autos.getNamed("center");
  }

  public void robotPeriodic() {
  }

  public void autonomousInit() {
  }

  public void autonomousPeriodic() {
  }

  public void teleopInit() {
  }

  public void teleopPeriodic() {
  }

  public void testInit() {
  }

  public void testPeriodic() {
  }

  public void simulationInit() {
  }

  public void simulationPeriodic() {

  }
}

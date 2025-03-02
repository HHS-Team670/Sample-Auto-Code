// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.subsystems.ExampleSubsystem;


import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final ExampleSubsystem m_exampleDrivetrain = new ExampleSubsystem();

  public RobotContainer() {
    configureBindings();
  }


  private void configureBindings() {
    // Buttons
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand(){

    Command talonFXCommand = Autos.exampleAuto(m_exampleDrivetrain.m_TalonFX);
    // For SparkMax
    // Command sparkMaxCommand = Autos.exampleAuto(m_exampleDrivetrain.m_SparkMax);

    return talonFXCommand;
  }
}

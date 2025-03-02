// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
  public TalonFX m_TalonFX = new TalonFX(25);
  public SparkMax m_SparkMax = new SparkMax(26, MotorType.kBrushless);
  

  public ExampleSubsystem() {
    //Some Motor Configuration
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}
}

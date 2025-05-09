// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team670.robot;

import static frc.team670.libs.IO.XboxJoysticButtons.driverUtils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Health.HealthChecker;
import frc.team670.libs.simulation.SimTalonFX;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.Auton.Autos;
import frc.team670.robot.commands.vision.AlignToClosestAprilTag;
import frc.team670.robot.constants.DrivetrainConstants;
import frc.team670.robot.subsystems.AlgaeManipulator;
import frc.team670.robot.subsystems.Arm;
import frc.team670.robot.subsystems.Claw;
import frc.team670.robot.subsystems.Climb;
import frc.team670.robot.subsystems.Drivetrain;
import frc.team670.robot.subsystems.Elevator;
import frc.team670.robot.subsystems.LED;
import frc.team670.robot.subsystems.Tilter;
import frc.team670.robot.subsystems.Vision;
import java.util.ArrayList;
import java.util.List;
import org.littletonrobotics.junction.Logger;

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
  Drivetrain mDrivetrain = Drivetrain.getInstance();
  Arm mArm = Arm.getInstance();
  Elevator mElevator = Elevator.getInstance();
  Tilter mTilter = Tilter.getInstance();
  Claw mClaw = Claw.getInstance();
  LED mLed = LED.getInstance();
  AlgaeManipulator mAlgaeManipulator = AlgaeManipulator.getInstance();
  Climb mClimb = Climb.getInstance();
  Vision mVision = Vision.getInstance();

  double lastSimTime = 0.0;

  public RobotContainer() {
    OI.configureBindings();
    registerSubsytems(mDrivetrain, mArm, mElevator, mTilter, mClaw, mLed, mClaw, mVision);
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
    Autos.configureAutoBuilder();
    return Autos.getNamed("right");
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

  public Timer timer = new Timer();

  public void simulationInit() {
    timer.start();

    List<Subsystem> allSubsystems = HealthChecker.getSubsystems();
    for (Subsystem sub : allSubsystems) {
      if (sub instanceof MotorizedSubsytem) {
      }
    }
  }

  public void simulationPeriodic() {
    double dt = timer.get();

    if (SimTalonFX.simMotors != null) {
      for (SimTalonFX sim : SimTalonFX.simMotors) {
        sim.update(dt);
        Logger.recordOutput(
            "Simulation/motors/" + sim.name + "/MotorPosition", sim.getSimPosition());
        Logger.recordOutput("Simulation/motors/" + sim.name + "/MotorTarget", sim.getSimTarget());
      }
    }

    Pose2d oldPose = mDrivetrain.getState().Pose;
    Pose2d newPose = oldPose.exp(
        new Twist2d(mDrivetrain.vxSim * dt, mDrivetrain.vySim * dt, mDrivetrain.omegaSim * dt));
    mDrivetrain.resetPose(newPose);

    if (DriverStation.isTeleopEnabled() && !AlignToClosestAprilTag.AligningToAprilTag) {
      mDrivetrain.vxSim = -driverUtils.getLeftStickY() * DrivetrainConstants.MaxSpeed;
      mDrivetrain.vySim = -driverUtils.getLeftStickX() * DrivetrainConstants.MaxSpeed;
      mDrivetrain.omegaSim = -driverUtils.getRightStickX() * DrivetrainConstants.MaxAngularRate;
    }

    List<Pose3d> subsystemPositions = new ArrayList<>();

    subsystemPositions.add(mElevator.calculateSimPose());

    Logger.recordOutput(
        "Simulation/RobotPositions/subsystems", subsystemPositions.toArray(Pose3d[]::new));
    Logger.recordOutput("Simulation/RobotPositions/robotPosition", mDrivetrain.getState().Pose);

    timer.restart();
  }
}

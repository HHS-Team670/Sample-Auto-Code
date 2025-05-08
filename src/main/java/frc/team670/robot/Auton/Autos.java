package frc.team670.robot.Auton;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.libs.Auto.AutoPath;
import frc.team670.libs.Auto.ChoreoCommand;
import frc.team670.robot.commands.claw.CoralIntake;
import frc.team670.robot.commands.claw.StartClawEject;
import frc.team670.robot.commands.vision.AlignToClosestAprilTag.CAMERA_SIDE;
import frc.team670.robot.commands.vision.PrepareShootCoral;
import frc.team670.robot.constants.RobotPosition;
import frc.team670.robot.subsystems.Drivetrain;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

public class Autos {

  private static final Map<String, AutoPath> autos = new HashMap<>();
  private static final SendableChooser<AutoPath> autoChooser = new SendableChooser<>();

  private static Command scoreCoral(String choreoPath, CAMERA_SIDE cameraSide) {
    return new SequentialCommandGroup(
        new ChoreoCommand(choreoPath),
        new PrepareShootCoral(RobotPosition.L4, cameraSide),
        new StartClawEject());
  }

  private static Command intakeCoral(String choreoPath) {
    return new ParallelCommandGroup(new ChoreoCommand(choreoPath), new CoralIntake());
  }

  public static void addAuto(String name, AutoPath path) {
    autos.put(name, path);
  }

  public static Command getSelected() {
    return autoChooser.getSelected().compileAuto();
  }

  public static Command getNamed(String name) {
    return autos.get(name).compileAuto();
  }

  static {
    configureAutoBuilder();
    AutoPath center = new AutoPath("center");
    center.addCommands(scoreCoral("C, 1R", CAMERA_SIDE.RIGHT));

    AutoPath left = new AutoPath("left");
    left.addCommands(
        scoreCoral("L, 5L", CAMERA_SIDE.LEFT),
        intakeCoral("5L, SL"),
        scoreCoral("SL, 5R", CAMERA_SIDE.RIGHT),
        intakeCoral("5R, SL"),
        scoreCoral("SL, 4L", CAMERA_SIDE.LEFT));

    AutoPath right = new AutoPath("right");
    right.addCommands(
        scoreCoral("R, 3R", CAMERA_SIDE.RIGHT),
        intakeCoral("3R, SR"),
        scoreCoral("SR, 3L", CAMERA_SIDE.LEFT),
        intakeCoral("3L, SR"),
        scoreCoral("SR, 4R", CAMERA_SIDE.RIGHT));

    autoChooser.setDefaultOption("center", center);
    autos.forEach(
        (name, path) -> {
          autoChooser.addOption(name, path);
        });
  }

  public static void configureAutoBuilder() {
    try {
      var config = RobotConfig.fromGUISettings();
      Drivetrain mDrivetrain = Drivetrain.getInstance();
      AutoBuilder.configure(
          () -> mDrivetrain.getState().Pose, // Supplier of current robot pose
          mDrivetrain::resetPose, // Consumer for seeding pose against auto
          () -> mDrivetrain.getState().Speeds, // Supplier of current robot speeds
          // Consumer of ChassisSpeeds and feedforwards to drive the robot
          (speeds, feedforwards) -> mDrivetrain.setControl(
              new SwerveRequest.ApplyRobotSpeeds()
                  .withSpeeds(speeds)
                  .withWheelForceFeedforwardsX(feedforwards.robotRelativeForcesXNewtons())
                  .withWheelForceFeedforwardsY(feedforwards.robotRelativeForcesYNewtons())),
          new PPHolonomicDriveController(
              // PID constants for translation
              new PIDConstants(10, 0, 0),
              // PID constants for rotation
              new PIDConstants(7, 0, 0)),
          config,
          // Assume the path needs to be flipped for Red vs Blue, this is normally the
          // case
          () -> false,
          mDrivetrain // Subsystem for requirements
      );

    } catch (Exception ex) {
      DriverStation.reportError(
          "Failed to load PathPlanner config and configure AutoBuilder", ex.getStackTrace());

    }
  }
}

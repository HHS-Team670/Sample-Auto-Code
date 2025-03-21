package frc.team670.robot.commands.auton;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.mustanglib.commands.MustangCommandConverter;
import frc.team670.robot.commands.Intake.StartClawIntake;
import frc.team670.robot.commands.claw.SetTilter;
import frc.team670.robot.commands.elevator.MoveToTarget;
import frc.team670.robot.subsystems.Claw;
import frc.team670.robot.subsystems.Claw.Tilter;
import frc.team670.robot.subsystems.Elevator;
import frc.team670.robot.subsystems.Elevator.ElevatorState;
import frc.team670.robot.subsystems.LED;
import frc.team670.robot.subsystems.drivebase.CommandSwerveDrivetrain;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.littletonrobotics.junction.Logger;

public final class Autos {
  public static CommandSwerveDrivetrain.MustangSwerveDrivetrain mDriveTrain =
      CommandSwerveDrivetrain.MustangSwerveDrivetrain.getInstance();

  public static Elevator mElevator = Elevator.getInstance();

  public static Claw mClaw = Claw.getInstance();

  public static Tilter mTilter = mClaw.getTilter();

  public static LED mLed = LED.getInstance();

  public static Map<String, List<Command>> map =
      Map.of(
          "Left-6-5",
          Arrays.asList(
              getChoreoCommand("L, 6L", true),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("6L, SL "),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 6R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("6R, SL"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 5L"),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("5L, SL"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 5R"),
              getScoreCommand()),
          "Right-2-3",
          Arrays.asList(
              getChoreoCommand("R, 2R", true),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("2R, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 2L"),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("2L, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 3R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("3R, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 3L"),
              getScoreCommand()),
          "Center-1-2",
          Arrays.asList(
              getChoreoCommand("C, 1R", true),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("1R, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 1L"),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("1L, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 2R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("2R, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 2L"),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand()),
          "Center-1-6",
          Arrays.asList(
              getChoreoCommand("C, 1L", true),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("1L, SL"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 1R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("1R, SL"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 6L"),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("6L, SL "),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 6R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand()),
          "Left-5-4",
          Arrays.asList(
              getChoreoCommand("L, 5L", true),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("5L, SL"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 5R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("5R, SL"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 4L"),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("4L, SL "),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SL, 4R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand()),
          "Right-3-4",
          Arrays.asList(
              getChoreoCommand("R, 3R", true),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("3R, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 3L"),
              // getVisionCommand(CoralSide.LEFT),
              getScoreCommand(),
              getChoreoCommand("3L, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 4R"),
              // getVisionCommand(CoralSide.RIGHT),
              getScoreCommand(),
              getChoreoCommand("4R, SR"),
              // getVisionCommand(CAMERA_SIDE.CENTER),
              getIntakeCommand(),
              getChoreoCommand("SR, 4L"),
              getScoreCommand()));

  public static Command getAutoCommand(String groupName) {
    List<Command> pathCommands = map.get(groupName);
    if (pathCommands != null) {
      Command first = pathCommands.get(0);
      for (int i = 1; i < pathCommands.size(); i++) {
        first = first.andThen(pathCommands.get(i));
      }
      return first;
    } else {
      return new InstantCommand(() -> Logger.recordOutput("Auton/groupfailed", groupName));
    }
  }

  private static Command getScoreCommand() {
    return new MoveToTarget(mElevator, ElevatorState.L4)
        // .andThen(getVisionCommand(CAMERA_SIDE.CENTER))
        .andThen(new SetTilter(mTilter, Claw.Tilter.Position.L4))
        .andThen(new StartClawEject(mClaw))
        .andThen(new MoveToTarget(mElevator, ElevatorState.STATION));
  }

  private static Command getIntakeCommand() {
    return new MoveToTarget(mElevator, ElevatorState.STATION)
        .andThen(new SetTilter(mTilter, Tilter.Position.STATION))
        .andThen(new StartClawIntake(mClaw, mLed));
  }

  // private static Command getVisionCommand(CoralSide coralSide) {
  // // CAMERA_SIDE cameraSide = coralSide == CoralSide.LEFT ? CAMERA_SIDE.RIGHT :
  // // CAMERA_SIDE.LEFT;

  // return null;
  // }

  // private static Command getVisionCommand(CAMERA_SIDE cameraSide) {
  // return null;
  // }

  public enum CoralSide {
    LEFT,
    RIGHT
  }

  private static Command getChoreoCommand(String pathName) {
    boolean first = false;
    try {
      PathPlannerPath test;
      if (first) {
        test = PathPlannerPath.fromChoreoTrajectory(pathName);
        PathPlannerTrajectory trajectory =
            test.generateTrajectory(
                mDriveTrain.getState().Speeds,
                mDriveTrain.getState().RawHeading,
                RobotConfig.fromGUISettings());
        mDriveTrain.resetPose(trajectory.getInitialPose());
      } else {
        test = PathPlannerPath.fromChoreoTrajectory(pathName);
      }

      Logger.recordOutput("Auton Path Failed", "false");

      return new MustangCommandConverter(AutoBuilder.followPath(test));
    } catch (Exception e) {
      Logger.recordOutput("Auton/PathStatus", "failed to get " + pathName + "first: " + first);
      Logger.recordOutput("Auton/ Path Error", e.getClass().getSimpleName());
    }
    return null;
  }

  private static Command getChoreoCommand(String pathName, boolean first) {

    try {
      PathPlannerPath test;
      if (first) {
        test = PathPlannerPath.fromChoreoTrajectory(pathName);
        PathPlannerTrajectory trajectory =
            test.generateTrajectory(
                mDriveTrain.getState().Speeds,
                mDriveTrain.getState().RawHeading,
                RobotConfig.fromGUISettings());
        mDriveTrain.resetPose(trajectory.getInitialPose());
      } else {
        test = PathPlannerPath.fromChoreoTrajectory(pathName);
      }

      Logger.recordOutput("Auton Path Failed", "false");

      return new MustangCommandConverter(AutoBuilder.followPath(test));
    } catch (Exception e) {
      Logger.recordOutput("Auton/PathStatus", "failed to get " + pathName + "first: " + first);
      Logger.recordOutput("Auton/ Path Error", e.getClass().getSimpleName());
    }
    return null;
  }
}

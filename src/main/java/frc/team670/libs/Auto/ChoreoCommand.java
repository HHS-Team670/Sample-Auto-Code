package frc.team670.libs.Auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import edu.wpi.first.math.geometry.Pose2d;
import frc.team670.libs.UtilityCommands.CopyCommand;
import frc.team670.robot.subsystems.Drivetrain;

public class ChoreoCommand extends CopyCommand {

  public PathPlannerPath path;
  public Drivetrain drivetrain = Drivetrain.getInstance();
  Pose2d initialPose2d;

  public ChoreoCommand(String path) {

    try {
      this.path = PathPlannerPath.fromChoreoTrajectory(path);
      PathPlannerTrajectory traj = this.path.generateTrajectory(
          drivetrain.getState().Speeds,
          drivetrain.getState().RawHeading,
          RobotConfig.fromGUISettings());
      initialPose2d = traj.getInitialPose();
      command = AutoBuilder.followPath(this.path);
      setCommand(command);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Error("Failed to make choreo Path");
    }
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    drivetrain.resetPose(initialPose2d);
    super.initialize();
  }
}

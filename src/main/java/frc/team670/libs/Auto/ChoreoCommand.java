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

  public ChoreoCommand(PathPlannerPath path) {
    this.path = path;
    try {
      PathPlannerTrajectory traj = path.generateTrajectory(
          drivetrain.getState().Speeds,
          drivetrain.getState().RawHeading,
          RobotConfig.fromGUISettings());
      initialPose2d = traj.getInitialPose();
      command = AutoBuilder.followPath(path);
      setCommand(command);
    } catch (Exception e) {
      e.printStackTrace();
    }
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    drivetrain.resetPose(initialPose2d);
    super.initialize();
  }
}

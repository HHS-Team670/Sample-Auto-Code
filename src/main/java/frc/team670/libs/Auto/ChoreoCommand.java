package frc.team670.libs.Auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.libs.subsystems.Drivetrain;

public class ChoreoCommand extends Command {

    public PathPlannerPath path;
    public Drivetrain drivetrain = Drivetrain.getInstance();
    Command command;
    Pose2d initialPose2d;


    public ChoreoCommand(PathPlannerPath path){
        this.path = path;
        try{
            PathPlannerTrajectory traj =
            path.generateTrajectory(
                drivetrain.getState().Speeds,
                drivetrain.getState().RawHeading,
                RobotConfig.fromGUISettings());
            initialPose2d = traj.getInitialPose();
            command = AutoBuilder.followPath(path);
        } catch (Exception e){
            e.printStackTrace();
        }
        addRequirements(drivetrain);
        addRequirements(command.getRequirements());
    }

    @Override
    public void initialize() {
        drivetrain.resetPose(initialPose2d);
        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public boolean isFinished() {
        return command.isFinished() || command == null;
    }

    @Override
    public void end(boolean interrupted) {
        command.end(interrupted);
    }
}

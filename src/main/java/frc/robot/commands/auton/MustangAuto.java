// package frc.team670.robot.commands.auton;

// import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.team670.mustanglib.commands.MustangCommandConverter;
// import frc.team670.robot.subsystems.drivebase.CommandSwerveDrivetrain;

// public class MustangAuto extends MustangCommandConverter {
//   private PathPlannerTrajectory traj;
//   private CommandSwerveDrivetrain.MustangSwerveDrivetrain drivetrain;

//   public MustangAuto(
//       Command command,
//       PathPlannerTrajectory traj,
//       CommandSwerveDrivetrain.MustangSwerveDrivetrain drivetrain) {
//     super(command);
//     this.traj = traj;
//     this.drivetrain = drivetrain;
//   }

//   @Override
//   public void initialize() {
//     drivetrain.resetPose(traj.getInitialPose());
//     super.initialize();
//   }
// }

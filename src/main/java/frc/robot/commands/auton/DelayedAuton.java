// package frc.team670.robot.commands.auton;

// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
// import frc.team670.mustanglib.commands.MustangCommand;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase.HealthState;
// import frc.team670.robot.subsystems.Elevator;
// import java.util.Map;

// public class DelayedAuton extends SequentialCommandGroup implements MustangCommand {

//   public DelayedAuton(double seconds, ParallelCommandGroup auton, Elevator elevator) {
//     super(new WaitCommand(seconds), auton);
//   }

//   @Override
//   public Map<MustangSubsystemBase, HealthState> getHealthRequirements() {
//     return null;
//   }
// }

// package frc.team670.robot.commands.auton;

// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.team670.mustanglib.commands.MustangCommand;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase.HealthState;
// import frc.team670.robot.commands.arm.SetArmMode;
// import frc.team670.robot.commands.claw.SetTilter;
// import frc.team670.robot.commands.elevator.MoveToTarget;
// import frc.team670.robot.subsystems.Arm;
// import frc.team670.robot.subsystems.Claw.Tilter;
// import frc.team670.robot.subsystems.Elevator;
// import frc.team670.robot.subsystems.Elevator.ElevatorState;
// import java.util.Map;

// public class AutonScoreCoral extends SequentialCommandGroup implements MustangCommand {
//   public AutonScoreCoral(Elevator elevator, Arm arm, Tilter tilter) {
//     super(
//         new SetArmMode(arm, Arm.Position.L4),
//         new MoveToTarget(elevator, ElevatorState.L4),
//         new SetTilter(tilter, Tilter.Position.L4));
//   }

//   @Override
//   public Map<MustangSubsystemBase, HealthState> getHealthRequirements() {
//     return null;
//   }
// }

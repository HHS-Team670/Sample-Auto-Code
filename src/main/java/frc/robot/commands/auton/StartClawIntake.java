// package frc.team670.robot.commands.auton;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.team670.mustanglib.commands.MustangCommand;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase.HealthState;
// import frc.team670.robot.subsystems.Claw;
// import java.util.Map;

// public class StartClawIntake extends Command implements MustangCommand {

//   Claw mClaw;

//   public StartClawIntake(Claw claw) {
//     mClaw = claw;
//     addRequirements(mClaw);
//   }

//   @Override
//   public void initialize() {
//     mClaw.setClawMode(Claw.Status.INTAKING);
//   }

//   public boolean isFinished() {
//     return mClaw.hasCoral();
//   }

//   @Override
//   public Map<MustangSubsystemBase, HealthState> getHealthRequirements() {
//     return null;
//   }
// }

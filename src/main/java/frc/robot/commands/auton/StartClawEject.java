// package frc.team670.robot.commands.auton;

// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.team670.mustanglib.commands.MustangCommand;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase.HealthState;
// import frc.team670.robot.constants.RobotConstants;
// import frc.team670.robot.subsystems.Claw;
// import java.util.Map;

// public class StartClawEject extends Command implements MustangCommand {
//   Claw mClaw;
//   Timer mTimer = new Timer();

//   public StartClawEject(Claw claw) {
//     addRequirements(claw);
//     this.mClaw = claw;
//   }

//   public void execute() {
//     mClaw.setClawMode(Claw.Status.EJECTING);
//     mTimer.start();
//   }

//   public boolean isFinished() {
//     return mTimer.hasElapsed(RobotConstants.Claw.kEjectTime);
//   }

//   public void end(boolean isInteruppted) {
//     mTimer.restart();
//     mTimer.stop();
//   }

//   @Override
//   public Map<MustangSubsystemBase, HealthState> getHealthRequirements() {
//     return null;
//   }
// }

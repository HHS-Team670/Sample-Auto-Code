package frc.team670.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.subsystems.Claw;

public class StartClawIntake extends InstantCommand {
  private Claw mClaw = Claw.getInstance();

  public StartClawIntake() {
    addRequirements(mClaw);
  }

  @Override
  public void initialize() {
    mClaw.setClawMode(Claw.Status.INTAKING);
  }
}

package frc.team670.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.commands.vision.AlignToClosestAprilTag;
import frc.team670.robot.subsystems.Claw;

public class StartClawEject extends InstantCommand {
  Claw mClaw = Claw.getInstance();

  public StartClawEject() {
    addRequirements(mClaw);
  }

  @Override
  public void initialize() {
    if (AlignToClosestAprilTag.AligningToAprilTag) {
      AlignToClosestAprilTag.AligningToAprilTag = false;
    } else {
      mClaw.setClawMode(Claw.Status.EJECTING);
    }
  }
}

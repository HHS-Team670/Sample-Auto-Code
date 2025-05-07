package frc.team670.robot.commands.tilter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.subsystems.Tilter;

public class TilterOffset extends InstantCommand {

  private Tilter mTilter = Tilter.getInstance();
  private boolean positive;

  public TilterOffset(boolean positive) {
    addRequirements(mTilter);
    this.positive = positive;
  }

  @Override
  public void initialize() {
    if (positive) {
      mTilter.addOffset(3);
    } else {
      mTilter.addOffset(-3);
    }
  }
}

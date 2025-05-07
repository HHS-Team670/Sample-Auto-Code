package frc.team670.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.subsystems.Climb;
import frc.team670.robot.subsystems.Climb.ClimbState;

public class SetClimbState extends InstantCommand {
  Climb mClimb = Climb.getInstance();
  ClimbState climbState;

  public SetClimbState(ClimbState climbState) {
    addRequirements(mClimb);
    this.climbState = climbState;
  }

  @Override
  public void initialize() {
    mClimb.setClimbMode(climbState);
  }
}

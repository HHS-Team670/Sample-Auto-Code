package frc.team670.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team670.robot.RobotPosition;
import frc.team670.robot.commands.drive.MoveToRobotPosition;

public class CoralIntake extends ParallelCommandGroup {
  public CoralIntake() {
    super(new MoveToRobotPosition(RobotPosition.STATION), new StartClawIntake());
  }
}

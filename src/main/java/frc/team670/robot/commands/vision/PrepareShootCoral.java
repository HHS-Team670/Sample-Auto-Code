package frc.team670.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.robot.commands.MoveToRobotPosition;
import frc.team670.robot.commands.vision.AlignToClosestAprilTag.CAMERA_SIDE;
import frc.team670.robot.constants.RobotPosition;

public class PrepareShootCoral extends SequentialCommandGroup {
  public PrepareShootCoral(RobotPosition robotPos) {
    super(
        new MoveToRobotPosition(robotPos),
        (RobotPosition.currentRobotPos == RobotPosition.L1
            ? new InstantCommand()
            : new AlignToClosestAprilTag(robotPos.equals(RobotPosition.L2))));
  }

  public PrepareShootCoral(RobotPosition robotPos, CAMERA_SIDE cameraSide) {
    super(
        new MoveToRobotPosition(robotPos),
        (RobotPosition.currentRobotPos == RobotPosition.L1
            ? new InstantCommand()
            : new AlignToClosestAprilTag(robotPos.equals(RobotPosition.L2), cameraSide)));
  }
}

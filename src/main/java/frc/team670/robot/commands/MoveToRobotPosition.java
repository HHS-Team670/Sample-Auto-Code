package frc.team670.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.constants.RobotPosition;
import frc.team670.robot.subsystems.Arm;
import frc.team670.robot.subsystems.Elevator;
import frc.team670.robot.subsystems.Tilter;

public class MoveToRobotPosition extends InstantCommand {
  RobotPosition robotPos;
  Arm mArm = Arm.getInstance();
  Tilter mTilter = Tilter.getInstance();
  Elevator mElevator = Elevator.getInstance();

  public MoveToRobotPosition(RobotPosition robotPos) {
    this.robotPos = robotPos;
  }

  @Override
  public void initialize() {
    mArm.setTargetPosition(robotPos);
    mTilter.setTargetPosition(robotPos);
    mElevator.setTargetHeight(robotPos);
    RobotPosition.currentRobotPos = robotPos;
  }
}

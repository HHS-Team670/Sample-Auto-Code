package frc.team670.robot.commands.AlgaeManipulator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team670.robot.commands.MoveToRobotPosition;
import frc.team670.robot.constants.RobotPosition;
import frc.team670.robot.subsystems.AlgaeManipulator;
import frc.team670.robot.subsystems.Arm;

public class ScoreBarge extends SequentialCommandGroup {
  AlgaeManipulator mAlgaeManipulator = AlgaeManipulator.getInstance();
  Arm mArm = Arm.getInstance();

  public ScoreBarge() {
    super(
        new MoveToRobotPosition(RobotPosition.SCOREBARGE),
        new WaitCommand(0.2),
        new AlgaeManipulatorIntake(false));
    addRequirements(mAlgaeManipulator, mArm);
  }
}

package frc.team670.robot.commands.AlgaeManipulator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.robot.commands.MoveToRobotPosition;
import frc.team670.robot.constants.RobotPosition;
import frc.team670.robot.subsystems.AlgaeManipulator;

public class ManipulateAlgae extends SequentialCommandGroup {
    AlgaeManipulator mAlgaeManipulator = AlgaeManipulator.getInstance();

    public ManipulateAlgae(
            boolean isIntaking, RobotPosition robotPos) {
        addRequirements(mAlgaeManipulator);
        if (isIntaking) {
            addCommands(
                    new MoveToRobotPosition(robotPos),
                    new AlgaeManipulatorIntake(isIntaking));
        } else {
            addCommands(
                    new MoveToRobotPosition(robotPos));
        }
    }
}
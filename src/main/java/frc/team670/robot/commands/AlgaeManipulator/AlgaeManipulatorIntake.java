package frc.team670.robot.commands.AlgaeManipulator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.OI;
import frc.team670.robot.subsystems.AlgaeManipulator;

public class AlgaeManipulatorIntake extends InstantCommand {
    private AlgaeManipulator mAlgaeManipulator = AlgaeManipulator.getInstance();
    private boolean isIntaking;

    public AlgaeManipulatorIntake(boolean isIntaking) {
        addRequirements(mAlgaeManipulator);
        this.isIntaking = isIntaking;
    }

    @Override
    public void initialize() {
        if (isIntaking) {
            mAlgaeManipulator.setAlgaeManipulatorMode(AlgaeManipulator.Mode.INTAKING);
        } else {
            mAlgaeManipulator.setAlgaeManipulatorMode(AlgaeManipulator.Mode.EJECTING);
            OI.coralModeOn = true;
        }
    }
}
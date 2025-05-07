package frc.team670.robot.commands.OI;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.OI;

import org.littletonrobotics.junction.Logger;

public class SetCoralMode extends InstantCommand {
    private boolean coralModeOn;

    public SetCoralMode(boolean coralModeOn) {
        this.coralModeOn = coralModeOn;
    }

    @Override
    public void initialize() {
        OI.coralModeOn = coralModeOn;
        Logger.recordOutput("OI/CoralModeOn", OI.coralModeOn);
    }

}
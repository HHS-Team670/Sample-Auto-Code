package frc.team670.robot.commands.OI;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.OI;
import frc.team670.robot.commands.vision.AlignToClosestAprilTag.CAMERA_SIDE;
import org.littletonrobotics.junction.Logger;

public class SetCameraSide extends InstantCommand {
  private CAMERA_SIDE cameraSide;

  public SetCameraSide(CAMERA_SIDE cameraSide) {
    this.cameraSide = cameraSide;
  }

  @Override
  public void initialize() {
    OI.cameraSide = this.cameraSide;
    Logger.recordOutput("OI/CameraSide", OI.cameraSide);
  }
}

package frc.team670.robot.commands;

import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.robot.subsystems.Arm;
import frc.team670.robot.subsystems.Climb;
import frc.team670.robot.subsystems.Elevator;
import frc.team670.robot.subsystems.Tilter;

public class Idle extends InstantCommand {

  private Arm mArm = Arm.getInstance();
  private Tilter mTilter = Tilter.getInstance();
  private Elevator mElevator = Elevator.getInstance();
  private Climb mClimb = Climb.getInstance();
  private int counter = 0;

  public Idle() {
    addRequirements(mArm, mTilter, mElevator, mClimb);
  }

  @Override
  public void initialize() {
    if (counter % 2 == 0) {
      mArm.getMotors(0).setNeutralMode(NeutralModeValue.Coast);
      mTilter.getMotors(0).setNeutralMode(NeutralModeValue.Coast);
      mElevator.getMotors(0).setNeutralMode(NeutralModeValue.Coast);
      mElevator.getMotors(1).setNeutralMode(NeutralModeValue.Coast);
      mClimb.getMotors(0).setNeutralMode(NeutralModeValue.Coast);
      counter++;
    } else {
      mArm.getMotors(0).setNeutralMode(NeutralModeValue.Brake);
      mTilter.getMotors(0).setNeutralMode(NeutralModeValue.Brake);
      mElevator.getMotors(0).setNeutralMode(NeutralModeValue.Brake);
      mElevator.getMotors(1).setNeutralMode(NeutralModeValue.Brake);
      mClimb.getMotors(0).setNeutralMode(NeutralModeValue.Brake);
      counter++;
    }
  }
}

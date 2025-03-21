package frc.team670.robot.commands.auton;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.mustanglib.commands.MustangCommand;
import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
import frc.team670.mustanglib.subsystems.MustangSubsystemBase.HealthState;
import frc.team670.robot.subsystems.drivebase.CommandSwerveDrivetrain;
import java.util.Map;

public class MoveBack extends Command implements MustangCommand {
  CommandSwerveDrivetrain.MustangSwerveDrivetrain drivetrain;
  SwerveRequest.RobotCentric drive;
  Timer timer = new Timer();
  int direction;

  public MoveBack(CommandSwerveDrivetrain.MustangSwerveDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    this.drive =
        new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    this.direction = 1;
  }

  public MoveBack(CommandSwerveDrivetrain.MustangSwerveDrivetrain drivetrain, boolean moveBack) {
    this.drivetrain = drivetrain;
    this.drive =
        new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    if (moveBack) {
      direction = 1;
    } else {
      direction = -1;
    }
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public void execute() {
    drivetrain.setControl(drive.withVelocityX(-0.3));
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(0.2);
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
  }

  @Override
  public Map<MustangSubsystemBase, HealthState> getHealthRequirements() {
    return null;
  }
}

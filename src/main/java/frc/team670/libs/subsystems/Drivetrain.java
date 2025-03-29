package frc.team670.libs.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveModule;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.robot.constants.DriveConstants;
import java.util.ArrayList;
import java.util.List;

public class Drivetrain extends SwerveDrivetrain<TalonFX, TalonFX, CANcoder>
    implements Subsystem, MustangSubsytem {
  public static Drivetrain mInstance = new Drivetrain();

  public static Drivetrain getInstance() {
    return mInstance;
  }

  private Drivetrain() {
    super(
        TalonFX::new,
        TalonFX::new,
        CANcoder::new,
        DriveConstants.drivetrainConstants,
        DriveConstants.moduleConstants);
  }

  @Override
  public List<TalonFX> getMotors() {
    List<TalonFX> motors = new ArrayList<>();
    for (SwerveModule<TalonFX, TalonFX, CANcoder> m : getModules()) {
      motors.add(m.getDriveMotor());
      motors.add(m.getSteerMotor());
    }
    return motors;
  }
}

package frc.team670.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveModule;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Health.HealthChecker;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.subsystems.DebugSubsytem;
import frc.team670.libs.subsystems.HealthySubsytem;
import frc.team670.libs.subsystems.MotorizedSubsytem;
import frc.team670.robot.constants.DrivetrainConstants;

import java.util.ArrayList;
import java.util.List;

public class Drivetrain extends SwerveDrivetrain<TalonFX, TalonFX, CANcoder>
    implements Subsystem, MotorizedSubsytem, HealthySubsytem, DebugSubsytem {
  public static Drivetrain mInstance = new Drivetrain();

  public static Drivetrain getInstance() {
    return mInstance;
  }

  private Drivetrain() {
    super(
        TalonFX::new,
        TalonFX::new,
        CANcoder::new,
        DrivetrainConstants.DrivetrainConstants,
        DrivetrainConstants.FrontLeft,
        DrivetrainConstants.FrontRight,
        DrivetrainConstants.BackLeft,
        DrivetrainConstants.BackRight);
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

  @Override
  public void periodic() {
    HealthChecker.reportHealth(this, checkHealth());
  }

  /**
   * For this specific subsytem it dosent matter because you will allways have a
   * DriveConstants
   * class
   *
   * @param constants
   */

  Health[] motorHealths = new Health[getModules().length * 2];

  @Override
  public Health checkHealth() {
    List<TalonFX> motors = getMotors();
    Health healthState = Health.UNKNOWN;

    int index = 0;
    for (TalonFX m : motors) {
      if (!TalonFXUtils.isHealthy(m)) {
        healthState = Health.YELLOW; // Never set drivetrain health to red
        motorHealths[index] = Health.RED;
      } else {
        motorHealths[index] = Health.GREEN;
      }
      index++;
    }
    if (healthState == Health.UNKNOWN) {
      healthState = Health.GREEN;
    }
    return healthState;
  }

  public void debugSubsystem() {

  }
}

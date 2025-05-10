package frc.team670.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.ApplyRobotSpeeds;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentric;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Health.HealthChecker;
import frc.team670.libs.Utilities.MustangMath;
import frc.team670.libs.Utilities.TalonFXUtils;
import frc.team670.libs.simulation.SimTalonFX;
import frc.team670.libs.subsystems.DebugSubsytem;
import frc.team670.libs.subsystems.HealthySubsytem;
import frc.team670.robot.constants.DrivetrainConstants;
import frc.team670.robot.robot.Robot;

import java.util.ArrayList;
import java.util.List;
import org.littletonrobotics.junction.Logger;

public class Drivetrain extends SwerveDrivetrain<TalonFX, TalonFX, CANcoder>
    implements Subsystem, HealthySubsytem, DebugSubsytem {
  public static Drivetrain mInstance = new Drivetrain();

  public List<SimTalonFX> motorSims = new ArrayList<>();

  public double vxSim = 0;
  public double vySim = 0;
  public double omegaSim = 0;

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

    for (TalonFX m : getMotors()) {
      motorSims.add(
          new SimTalonFX(
              m,
              TalonFXUtils.getConfig(m).MotionMagic.MotionMagicCruiseVelocity,
              TalonFXUtils.getConfig(m).MotionMagic.MotionMagicAcceleration,
              "DrivetrainMotor",
              0,
              true));
    }
  }

  int count = 0;

  @Override
  public void setControl(SwerveRequest request) {
    if (Robot.isSimulation()) {
      if (request instanceof ApplyRobotSpeeds) {
        ApplyRobotSpeeds reqSpeeds = (ApplyRobotSpeeds) request;
        ChassisSpeeds speeds = reqSpeeds.Speeds;
        vxSim = speeds.vxMetersPerSecond;
        vySim = speeds.vyMetersPerSecond;
        omegaSim = speeds.omegaRadiansPerSecond;
      } else if (request instanceof FieldCentric) {
        FieldCentric fieldReq = (FieldCentric) request;
        double vFieldX = fieldReq.VelocityX;
        double vFieldY = fieldReq.VelocityY;

        double currentRot = getState().Pose.getRotation().getRadians();

        double vRobotX = vFieldX * Math.cos(currentRot) + vFieldY * Math.sin(currentRot);
        double vRobotY = -vFieldX * Math.sin(currentRot) + vFieldY * Math.cos(currentRot);

        vxSim = vRobotX;
        vySim = vRobotY;
      }
    } else {
      super.setControl(request);
    }
  }

  public List<TalonFX> getMotors() {
    List<TalonFX> motors = new ArrayList<>();

    for (SwerveModule<TalonFX, TalonFX, CANcoder> m : getModules()) {
      motors.add(m.getDriveMotor());
      motors.add(m.getSteerMotor());
    }
    return motors;
  }

  public double metersToMotorRotations(double meters) {
    double wheelDegrees = meters / (DrivetrainConstants.kWheelRadius.baseUnitMagnitude() * 2 * Math.PI) * 360;
    return MustangMath.getRotationsFromDegrees(DrivetrainConstants.kDriveGearRatio, wheelDegrees);
  }

  @Override
  public void periodic() {
    HealthChecker.reportHealth(this, checkHealth());
    debugSubsystem();
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
    Logger.recordOutput(this.getName() + "/CurrentPose2d", this.getState().Pose);
  }
}

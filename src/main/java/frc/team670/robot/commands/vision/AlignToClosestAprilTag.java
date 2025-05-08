package frc.team670.robot.commands.vision;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.robot.OI;
import frc.team670.robot.Robot;
import frc.team670.robot.subsystems.Drivetrain;
import frc.team670.robot.subsystems.Vision;
import org.photonvision.targeting.PhotonTrackedTarget;

public class AlignToClosestAprilTag extends Command {
  public enum CAMERA_SIDE {
    LEFT,
    RIGHT,
    CENTER
  }

  public static boolean AligningToAprilTag = false;

  public static PhotonTrackedTarget aprilTag;
  private Vision mVision = Vision.getInstance();
  String cameraName;

  private Timer timer = new Timer();

  SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric()
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private Drivetrain mDrivetrain = Drivetrain.getInstance();

  private double xDist = 0;
  private double yDist = 0;
  private double rotation = 0;

  double xValue = 0;
  double yValue = 0;
  double rotationValue = 0;

  private boolean hasFoundAprilTag;

  private double metersBack;

  private CAMERA_SIDE cameraSide = null;

  public static int simTag;

  // Adjust these modifiers as needed
  private static final double xSpeedModifier = 1;
  private static final double ySpeedModifier = 2.2;
  private static final double rotationSpeedModifier = 4;
  private static final double xAdjustment = 0.25;
  private static final double yAdjustment = 0;
  private static final double rotationAdjustment = 0;

  // These describe how far away from being perfectly aligned the robot should go
  // to (robot centric)
  private static final double xOffset = 0;
  private static final double yOffset = 0;

  public AlignToClosestAprilTag(boolean isLevelL2) {
    addRequirements(mVision, mDrivetrain);
  }

  public AlignToClosestAprilTag(boolean isLevelL2, CAMERA_SIDE cameraSide) {
    addRequirements(mVision, mDrivetrain);
    this.cameraSide = cameraSide;
  }

  @Override
  public void initialize() {
    timer.start();

    mDrivetrain.setControl(drive.withVelocityY(0).withVelocityX(0));

    CAMERA_SIDE cameraSideToUse = (this.cameraSide == null ? OI.cameraSide : this.cameraSide);

    if (cameraSideToUse == CAMERA_SIDE.LEFT) {
      this.cameraName = "ArducamL";
      // Assign meters back to how far the camera is from the front of the bumper
      metersBack = Units.inchesToMeters(6.3);
    } else if (cameraSideToUse == CAMERA_SIDE.RIGHT) {
      this.cameraName = "ArducamR";
      // Assign meters back to how far the camera is from the front of the bumper
      metersBack = Units.inchesToMeters(6.3);
    }

    hasFoundAprilTag = false;
    AligningToAprilTag = true;
    mVision.lastSeenAprilTagLeftCam = null;
    mVision.lastSeenAprilTagRightCam = null;
    mVision.lastRobotPoseLeftCam = new Pose2d(0, 0, new Rotation2d());
    mVision.lastRobotPoseRightCam = new Pose2d(0, 0, new Rotation2d());
    mVision.robotCentricChangeSinceSeenLeftCamAprilTag = new Pose2d(0, 0, new Rotation2d());
    mVision.robotCentricChangeSinceSeenRightCamAprilTag = new Pose2d(0, 0, new Rotation2d());
  }

  @Override
  public void execute() {
    aprilTag = cameraName == "ArducamL" ? mVision.leftCamAprilTag : mVision.rightCamAprilTag;

    if (aprilTag != null) {
      hasFoundAprilTag = true;
      Transform3d normalTag = aprilTag.getBestCameraToTarget();
      Pose3d target = new Pose3d(normalTag.getX(), normalTag.getY(), normalTag.getZ(), normalTag.getRotation());
      if (Robot.isSimulation()) {
        target = mVision.getClosestSimTarget(cameraSide);
      }

      xDist = target.getX() - metersBack - xOffset;
      if (xDist < 0) {
        xDist = 0;
      }
      yDist = target.getY() - yOffset;
      rotation = target.getRotation().toRotation2d().getRadians();
      if (rotation < 0) {
        rotation = Math.PI + rotation;
      } else {
        rotation = Math.PI - rotation;
      }
    } else if (hasFoundAprilTag) {
      try {
        Pose2d lastAprilTag = cameraName == "ArducamL"
            ? mVision.lastSeenAprilTagLeftCam
            : mVision.lastSeenAprilTagRightCam;

        Pose2d lastRobotCentriChange = cameraName == "ArducamL"
            ? mVision.robotCentricChangeSinceSeenLeftCamAprilTag
            : mVision.robotCentricChangeSinceSeenRightCamAprilTag;

        double xChange = lastRobotCentriChange.getX();
        double yChange = lastRobotCentriChange.getY();

        xDist = lastAprilTag.getX() - xChange - metersBack - xOffset;
        yDist = lastAprilTag.getY() - yChange - yOffset;

        double lastAprilTagRotation = lastAprilTag.getRotation().getRadians();
        if (lastAprilTagRotation > 0) {
          lastAprilTagRotation -= Math.PI;
        } else {
          lastAprilTagRotation += Math.PI;
        }
        rotation = lastAprilTagRotation + (lastRobotCentriChange.getRotation().getRadians());
      } catch (Exception error) {
      }
    }

    if (Math.abs(rotation) < Units.degreesToRadians(2)) {
      rotation = 0;
    }

    xValue = (xDist * xSpeedModifier + xAdjustment);
    yValue = (yDist * ySpeedModifier + yAdjustment);
    rotationValue = (rotation * rotationSpeedModifier + rotationAdjustment);

    mDrivetrain.vxSim = xValue;
    mDrivetrain.vySim = yValue;
    mDrivetrain.omegaSim = rotationValue;

    mDrivetrain.setControl(
        drive.withVelocityX(xValue).withVelocityY(yValue).withRotationalRate(rotationValue));
  }

  @Override
  public boolean isFinished() {
    // Exit switch toggle this in another command
    if (!AligningToAprilTag) {
      return true;
    }

    if (DriverStation.isAutonomousEnabled() && timer.hasElapsed(3)) {
      return true;
    }

    return (Math.abs(yDist) < 0.02) && (Math.abs(xDist) < 0.08) && hasFoundAprilTag;
  }

  @Override
  public void end(boolean interrupted) {
    mDrivetrain.setControl(drive.withVelocityY(0).withVelocityX(0).withRotationalRate(0));
    xValue = 0;
    yValue = 0;
    rotationValue = 0;
    AligningToAprilTag = false;

    mDrivetrain.vxSim = 0;
    mDrivetrain.vySim = 0;
    mDrivetrain.omegaSim = 0;

    timer.stop();
    timer.reset();
  }
}

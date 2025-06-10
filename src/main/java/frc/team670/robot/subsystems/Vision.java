package frc.team670.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFieldLayout.OriginPosition;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.libs.Health.Health;
import frc.team670.libs.Health.HealthChecker;
import frc.team670.libs.subsystems.DebugSubsytem;
import frc.team670.libs.subsystems.HealthySubsytem;
import frc.team670.robot.OI;
import frc.team670.robot.constants.AprilTagConstants;
import frc.team670.robot.constants.VisionConstants;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonTrackedTarget;

/**
 * Subsystem base vision. Mainly used for april tags pose estimation.
 *
 * @author ethan c
 */
public class Vision implements Subsystem, HealthySubsytem, DebugSubsytem {
  private AprilTagFieldLayout kFieldLayout;

  protected PhotonCamera[] mCameras;
  private boolean mInit = false;

  public int[] allowedAprilTags = null;

  private static Drivetrain mDrivetrain;

  public Pose2d lastSeenAprilTagLeftCam = new Pose2d(0, 0, new Rotation2d());
  public Pose2d lastSeenAprilTagRightCam = new Pose2d(0, 0, new Rotation2d());
  public Pose2d lastRobotPoseLeftCam = new Pose2d(0, 0, new Rotation2d());
  public Pose2d lastRobotPoseRightCam = new Pose2d(0, 0, new Rotation2d());
  public Pose2d robotCentricChangeSinceSeenLeftCamAprilTag = new Pose2d(0, 0, new Rotation2d());
  public Pose2d robotCentricChangeSinceSeenRightCamAprilTag = new Pose2d(0, 0, new Rotation2d());
  public PhotonTrackedTarget leftCamAprilTag = null;
  public PhotonTrackedTarget rightCamAprilTag = null;

  public static Vision mInstance;

  public static synchronized Vision getInstance() {
    mInstance = mInstance == null ? new Vision() : mInstance;
    return mInstance;
  }

  private Vision() {
    this.kFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
    this.mCameras = new PhotonCamera[VisionConstants.kVisionCameraIDs.length];
    for (int i = 0; i < mCameras.length; i++) {
      mCameras[i] = new PhotonCamera(VisionConstants.kVisionCameraIDs[i]);
      mCameras[i].setLED(VisionLEDMode.kOff);
    }
  }

  private PhotonTrackedTarget getAprilTag(
      PhotonCamera[] cameras, String nameAssignment, int[] allowedAprilTags) {
    try {
      double bestRotation = 0;
      PhotonTrackedTarget bestTarget = null;
      PhotonCamera goodCamera = null;
      for (PhotonCamera camera : cameras) {
        if (camera.getName() == nameAssignment) {
          goodCamera = camera;
          break;
        }
      }
      if (goodCamera == null) {
        return null;
      }
      var results = goodCamera.getAllUnreadResults();
      if (!results.isEmpty()) {
        for (var result : results) {
          if (result.hasTargets()) {
            var targets = result.getTargets();
            for (var target : targets) {
              for (int aprilTagNumber : allowedAprilTags) {
                if (target.getFiducialId() == aprilTagNumber) {
                  double angle =
                      Math.abs(
                          target.getBestCameraToTarget().getRotation().toRotation2d().getDegrees());
                  if (angle < 160) {
                    continue;
                  }
                  if (angle > bestRotation) {
                    bestRotation = angle;
                    bestTarget = target;
                  }
                }
              }
            }
          }
        }
      }
      return bestTarget;
    } catch (java.lang.IllegalArgumentException e) {
    }
    return null;
  }

  private void saveTranslationToLastAprilTagDetected() {
    leftCamAprilTag =
        getAprilTag(
            mCameras,
            "ArducamL",
            this.allowedAprilTags == null
                ? OI.alliance == DriverStation.Alliance.Red
                    ? AprilTagConstants.redReefTags
                    : AprilTagConstants.blueReefTags
                : this.allowedAprilTags);
    rightCamAprilTag =
        getAprilTag(
            mCameras,
            "ArducamR",
            this.allowedAprilTags == null
                ? OI.alliance == DriverStation.Alliance.Red
                    ? AprilTagConstants.redReefTags
                    : AprilTagConstants.blueReefTags
                : this.allowedAprilTags);

    Pose2d currentPose = mDrivetrain.getState().Pose;

    if (leftCamAprilTag != null) {
      Transform3d bestCamToTarget = leftCamAprilTag.getBestCameraToTarget();
      lastSeenAprilTagLeftCam =
          new Pose2d(
              bestCamToTarget.getX(),
              bestCamToTarget.getY(),
              bestCamToTarget.getRotation().toRotation2d());

      lastRobotPoseLeftCam = currentPose;
      robotCentricChangeSinceSeenLeftCamAprilTag = new Pose2d(0, 0, new Rotation2d(0));
    } else {
      robotCentricChangeSinceSeenLeftCamAprilTag = getRobotCentricChange(true);
    }

    if (rightCamAprilTag != null) {
      Transform3d bestCamToTarget = rightCamAprilTag.getBestCameraToTarget();
      lastSeenAprilTagRightCam =
          new Pose2d(
              bestCamToTarget.getX(),
              bestCamToTarget.getY(),
              bestCamToTarget.getRotation().toRotation2d());

      lastRobotPoseRightCam = currentPose;
      robotCentricChangeSinceSeenRightCamAprilTag = new Pose2d(0, 0, new Rotation2d(0));
    } else {
      robotCentricChangeSinceSeenRightCamAprilTag = getRobotCentricChange(false);
    }
  }

  private Pose2d getRobotCentricChange(boolean isLeftCamera) {
    Pose2d lastRobotPose = isLeftCamera ? lastRobotPoseLeftCam : lastRobotPoseRightCam;
    Pose2d currentRobotPose = mDrivetrain.getState().Pose;
    double xChangeField = currentRobotPose.getX() - lastRobotPose.getX();
    double yChangeField = currentRobotPose.getY() - lastRobotPose.getY();
    Rotation2d rotationChange = currentRobotPose.getRotation().minus(lastRobotPose.getRotation());
    double distanceChange = Math.sqrt(Math.pow(xChangeField, 2) + Math.pow(yChangeField, 2));

    if (yChangeField == 0) {
      yChangeField = 0.00000000000000001;
    }

    // Fieldcentric to robotcentric calculations
    // (https://www.canva.com/design/DAGgRZjmWXI/TYRSSrswz4FQHHXcytOO3g/view)
    double fieldToRobotCentricAngle =
        currentRobotPose.getRotation().getRadians() * -1 + Math.atan(xChangeField / yChangeField);
    double xChange = Math.cos(fieldToRobotCentricAngle) * distanceChange;
    double yChange = Math.sin(fieldToRobotCentricAngle) * distanceChange;
    if (currentRobotPose.getY() < lastRobotPose.getY()) {
      xChange *= -1;
      yChange *= -1;
    }
    return new Pose2d(xChange, yChange, rotationChange);
  }

  public void debugSubsystem() {}

  /**
   * Initalizes the vision subsystem. DO NOT CALL IN ROBOT INIT! DS IS NOT NECESSARILY READY THEN.
   * CALL IN PERIODIC OR AUTONINIT. More details here:
   * https://www.chiefdelphi.com/t/getalliance-always-returning-red/425782/27
   */
  public void initalize() {
    // does nothing if DS not initialized yet
    if (!DriverStation.getAlliance().isPresent()) {
      mInit = false;
      return;
    }
    setFieldOrigin();

    mInit = true;
  }

  /** Sets origin based on field side (red alliance or blue alliance) */
  private void setFieldOrigin() {
    var origin =
        DriverStation.getAlliance().get() == Alliance.Blue
            ? OriginPosition.kBlueAllianceWallRightSide
            : OriginPosition.kRedAllianceWallRightSide;
    kFieldLayout.setOrigin(origin);
  }

  /** Attempts to initalize vision and estimate robot pose after processing the vision feed */
  @Override
  public void periodic() {
    saveTranslationToLastAprilTagDetected();

    // attempt initialization until initialized
    if (!mInit) {
      initalize();
      return;
    }

    HealthChecker.reportHealth(this, checkHealth());
  }

  /** A representation of the visions pose estimation and its confidence */
  public record VisionMeasurement(EstimatedRobotPose estimation, Vector<N3> confidence) {}

  /**
   * @return the healthstate of the vision subsystem
   */
  @Override
  public Health checkHealth() {
    Health state = Health.GREEN;
    int counter = 0;
    // checks through the photon cameras and checks if they are null or !connected
    for (PhotonCamera camera : mCameras) {
      if (camera == null || !camera.isConnected()) {
        state = Health.YELLOW;
        counter++;
        if (camera.getName() == "ArducamL") {}
      } else {
        if (camera.getName() == "ArducamR") {}
      }
    }
    // iff all of the cameras are null or not connected healthstate = red
    if (counter == mCameras.length && mCameras.length != 0) {
      state = Health.YELLOW;
    }
    return state;
  }

  /**
   * @return the cameras this subsystem uses
   */
  public PhotonCamera[] getCameras() {
    return mCameras;
  }
}

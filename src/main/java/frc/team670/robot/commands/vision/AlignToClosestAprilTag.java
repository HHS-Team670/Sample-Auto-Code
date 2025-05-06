package frc.team670.robot.commands.vision;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.robot.OI;
import frc.team670.robot.subsystems.Vision;
import frc.team670.robot.subsystems.Drivetrain;
import org.littletonrobotics.junction.Logger;
import org.photonvision.targeting.PhotonTrackedTarget;

public class AlignToClosestAprilTag extends Command {
    public enum CAMERA_SIDE {
        LEFT,
        RIGHT,
        CENTER
    }

    public static boolean AligningToAprilTag = false;

    public static PhotonTrackedTarget aprilTag;
    private Vision mVision;
    String nameAssignment;

    SwerveRequest.RobotCentric drive;

    private Drivetrain mDrivetrain;

    private double xDist = 0;
    private double yDist = 0;
    private double rotation = 0;
    private static final double xSpeedModifier = 1;
    private static final double ySpeedModifier = 2.2;
    private static final double rotationSpeedModifier = 4;
    private static final double xAdjustment = 0.25;
    private static final double yAdjustment = 0;
    private static final double rotationAdjustment = 0;

    private boolean hasFoundAprilTag;

    private double metersBack;

    double xValue = 0;
    double yValue = 0;
    double rotationValue = 0;

    public AlignToClosestAprilTag(
            Drivetrain mDrivetrain, Vision mVision) {
        addRequirements(mVision, mDrivetrain);
        this.mDrivetrain = mDrivetrain;
        this.mVision = mVision;
        this.drive = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    }

    @Override
    public void initialize() {
        mDrivetrain.setControl(drive.withVelocityY(0).withVelocityX(0));
        if (OI.cameraSide == CAMERA_SIDE.LEFT) {
            this.nameAssignment = "ArducamL";
            metersBack = Units.inchesToMeters(6.3);
        } else if (OI.cameraSide == CAMERA_SIDE.RIGHT) {
            this.nameAssignment = "ArducamR";
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
        aprilTag = nameAssignment == "ArducamL" ? mVision.leftCamAprilTag : mVision.rightCamAprilTag;

        if (aprilTag != null) {
            Logger.recordOutput("Vision/APRILTAGID", aprilTag.getFiducialId());
            hasFoundAprilTag = true;
            Transform3d target = aprilTag.getBestCameraToTarget();
            xDist = target.getX() - metersBack;
            if (xDist < 0) {
                xDist = 0;
            }
            yDist = target.getY();
            rotation = target.getRotation().toRotation2d().getRadians();
            if (rotation < 0) {
                rotation = Math.PI + rotation;
            } else {
                rotation = Math.PI - rotation;
            }
        } else if (hasFoundAprilTag) {
            try {
                Pose2d lastAprilTag = nameAssignment == "ArducamL"
                        ? mVision.lastSeenAprilTagLeftCam
                        : mVision.lastSeenAprilTagRightCam;

                Pose2d lastRobotCentriChange = nameAssignment == "ArducamL"
                        ? mVision.robotCentricChangeSinceSeenLeftCamAprilTag
                        : mVision.robotCentricChangeSinceSeenRightCamAprilTag;

                double xChange = lastRobotCentriChange.getX();
                double yChange = lastRobotCentriChange.getY();

                Logger.recordOutput("Vision/XChange", xChange);
                Logger.recordOutput("Vision/YChange", yChange);

                xDist = lastAprilTag.getX() - xChange - metersBack;
                yDist = lastAprilTag.getY() - yChange;

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

        Logger.recordOutput("Vision/HasSeenAprilTag", hasFoundAprilTag);
        Logger.recordOutput("Vision/XDist", xDist);
        Logger.recordOutput("Vision/YDist", yDist);
        Logger.recordOutput("Vision/RotationOffset", Units.radiansToDegrees(rotation));

        xValue = (xDist * xSpeedModifier + xAdjustment);
        yValue = (yDist * ySpeedModifier + yAdjustment);
        rotationValue = (rotation * rotationSpeedModifier + rotationAdjustment);

        Logger.recordOutput("Vision/XValue", xValue);
        Logger.recordOutput("Vision/YValue", yValue);
        Logger.recordOutput("Vision/RotationValue", rotationValue);

        mDrivetrain.setControl(
                drive.withVelocityX(xValue).withVelocityY(yValue).withRotationalRate(rotationValue));
    }

    @Override
    public boolean isFinished() {
        if (!AligningToAprilTag) {
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
    }
}
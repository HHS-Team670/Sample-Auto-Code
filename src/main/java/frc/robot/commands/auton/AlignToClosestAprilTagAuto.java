// package frc.team670.robot.commands.auton;

// import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
// import com.ctre.phoenix6.swerve.SwerveRequest;
// import edu.wpi.first.math.geometry.Transform3d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.team670.mustanglib.commands.MustangCommand;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
// import frc.team670.mustanglib.subsystems.MustangSubsystemBase.HealthState;
// import frc.team670.robot.commands.vision.AlignToClosestAprilTag.CAMERA_SIDE;
// import frc.team670.robot.subsystems.Vision;
// import frc.team670.robot.subsystems.drivebase.CommandSwerveDrivetrain;
// import java.util.Map;
// import org.littletonrobotics.junction.Logger;
// import org.photonvision.PhotonCamera;
// import org.photonvision.targeting.PhotonTrackedTarget;

// public class AlignToClosestAprilTagAuto extends Command implements MustangCommand {
//   public static PhotonTrackedTarget aprilTag;
//   private PhotonCamera[] cameras;
//   private Vision mVision;
//   String nameAssignment;

//   SwerveRequest.RobotCentric drive;

//   private CommandSwerveDrivetrain.MustangSwerveDrivetrain driveBase;

//   private int[] allowedAprilTags;

//   boolean hasStoppedForCounter;

//   private double xDist = 0;
//   private double yDist = 0;
//   private double rotation = 0;
//   private static final double xSpeedModifier = 0.7;
//   private static final double ySpeedModifier = 2.1;
//   private static final double rotationSpeedModifier = 0.1;
//   private static final double xAdjustment = 0.08;
//   private static final double yAdjustment = 0.08;
//   private static final double rotationAdjustment = 0;

//   private int counter;

//   private boolean hasFoundAprilTag;

//   private double metersBack;

//   CAMERA_SIDE cameraSide;
//   private Timer mTimer = new Timer();

//   public AlignToClosestAprilTagAuto(
//       CommandSwerveDrivetrain.MustangSwerveDrivetrain driveBase,
//       Vision mVision,
//       CAMERA_SIDE cameraSide,
//       int allowedAprilTag) {
//     addRequirements(mVision, driveBase);
//     this.driveBase = driveBase;
//     this.mVision = mVision;
//     this.allowedAprilTags = new int[] {allowedAprilTag};
//     this.drive =
//         new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
//     this.cameraSide = cameraSide;
//   }

//   @Override
//   public Map<MustangSubsystemBase, HealthState> getHealthRequirements() {
//     return null;
//   }

//   @Override
//   public void initialize() {
//     mTimer.restart();
//     if (cameraSide == CAMERA_SIDE.LEFT) {
//       this.nameAssignment = "ArducamL";
//       metersBack = Units.inchesToMeters(6.3);
//     } else if (cameraSide == CAMERA_SIDE.RIGHT) {
//       this.nameAssignment = "ArducamR";
//       metersBack = Units.inchesToMeters(6.3);
//     } else {
//       this.nameAssignment = "ArducamSTATION";
//       metersBack = Units.inchesToMeters(0);
//     }
//     counter = 0;
//     hasStoppedForCounter = false;
//     this.cameras = mVision.getCameras();
//     hasFoundAprilTag = false;
//     Logger.recordOutput("Vision/HasStoppedForCounter", hasStoppedForCounter);
//   }

//   @Override
//   public void execute() {
//     Logger.recordOutput("Vision/RUNNING", true);
//     aprilTag = mVision.getAprilTag(cameras, nameAssignment, allowedAprilTags);
//     Logger.recordOutput("Vision/AprilTagVisible", aprilTag != null);
//     if (aprilTag != null) {
//       hasFoundAprilTag = true;
//       counter = 0;
//       Transform3d target = aprilTag.getBestCameraToTarget();
//       xDist = target.getX() - metersBack;
//       if (xDist < 0) {
//         xDist = 0;
//       }
//       yDist = target.getY();
//       rotation = target.getRotation().getAngle();
//       if (rotation < 0) {
//         rotation = Math.PI + rotation;
//       } else {
//         rotation = Math.PI - rotation;
//       }
//     } else {
//       counter++;
//       if (counter > 150) {
//         hasStoppedForCounter = true;
//         xDist = 0;
//         yDist = 0;
//       }
//     }
//     Logger.recordOutput("Vision/HasSeenAprilTag", hasFoundAprilTag);
//     Logger.recordOutput("Vision/XDist", xDist);
//     Logger.recordOutput("Vision/YDist", yDist);
//     Logger.recordOutput("Vision/RotationOffset", Units.radiansToDegrees(rotation));

//     double xValue = xDist * xSpeedModifier + xAdjustment;
//     double yValue = yDist * ySpeedModifier + yAdjustment;
//     double rotationValue = rotation * rotationSpeedModifier + rotationAdjustment;

//     Logger.recordOutput("Vision/XValue", xValue);
//     Logger.recordOutput("Vision/YValue", yValue);
//     Logger.recordOutput("Vision/RotationValue", rotationValue);

//     driveBase.setControl(
//         drive.withVelocityX(xValue).withVelocityY(yValue).withRotationalRate(rotationValue));
//   }

//   @Override
//   public boolean isFinished() {
//     return ((Math.abs(xDist) < 0.05) && hasFoundAprilTag) || mTimer.hasElapsed(11);
//   }

//   @Override
//   public void end(boolean interrupted) {
//     Logger.recordOutput("Vision/RUNNING", false);
//     driveBase.setmDesiredHeading(null);
//     driveBase.setControl(drive.withVelocityY(0).withVelocityX(0));
//     xDist = 0;
//     yDist = 0;
//     Logger.recordOutput("Vision/HasStoppedForCounter", hasStoppedForCounter);
//   }
// }

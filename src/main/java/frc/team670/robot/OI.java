package frc.team670.robot;

/**
 * All of the buttons you may need to bind are from this static import it also has utils for
 * controlers Called driverUtils, operatorUtils of type {@link ControllerUtils} Format
 * Controller_Button ex. Driver_ButtonA, Driver_LeftBumper, Diver_Dpad_West
 */
import static frc.team670.libs.IO.XboxJoysticButtons.*;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.libs.UtilityCommands.ButtonCommand;
import frc.team670.robot.commands.AlgaeManipulator.AlgaeManipulatorIntake;
import frc.team670.robot.commands.AlgaeManipulator.ManipulateAlgae;
import frc.team670.robot.commands.OI.SetCameraSide;
import frc.team670.robot.commands.OI.SetCoralMode;
import frc.team670.robot.commands.claw.CoralIntake;
import frc.team670.robot.commands.claw.StartClawEject;
import frc.team670.robot.commands.climb.SetClimbState;
import frc.team670.robot.commands.drive.Idle;
import frc.team670.robot.commands.drive.Park;
import frc.team670.robot.commands.tilter.TilterOffset;
import frc.team670.robot.commands.vision.AlignToClosestAprilTag.CAMERA_SIDE;
import frc.team670.robot.commands.vision.PrepareShootCoral;
import frc.team670.robot.constants.DrivetrainConstants;
import frc.team670.robot.subsystems.Arm;
import frc.team670.robot.subsystems.Climb.ClimbState;
import frc.team670.robot.subsystems.Drivetrain;
import frc.team670.robot.subsystems.Elevator;

public class OI {

  public static XboxController driver = new XboxController(0);
  public static XboxController operator = new XboxController(1);

  public static Alliance alliance;
  public static CAMERA_SIDE cameraSide;
  public static boolean coralModeOn;

  static Drivetrain mDrivetrain = Drivetrain.getInstance();

  public static Boolean isCoralModeOn() {
    return coralModeOn;
  }

  public static void configureDriverControls() {
    Driver_RightBumper.onTrue(
        new ButtonCommand(new CoralIntake(), new AlgaeManipulatorIntake(true), OI::isCoralModeOn));
    Driver_LeftBumper.onTrue(
        new ButtonCommand(
            new StartClawEject(), new AlgaeManipulatorIntake(false), OI::isCoralModeOn));
    Driver_ButtonA.onTrue(
        new ButtonCommand(
            new PrepareShootCoral(RobotPosition.L1),
            new ManipulateAlgae(false, RobotPosition.PROCESSOR),
            OI::isCoralModeOn));
    Driver_ButtonX.onTrue(
        new ButtonCommand(
            new PrepareShootCoral(RobotPosition.L2),
            new ManipulateAlgae(false, RobotPosition.ALGAE23),
            OI::isCoralModeOn));
    Driver_ButtonB.onTrue(
        new ButtonCommand(
            new PrepareShootCoral(RobotPosition.L3),
            new ManipulateAlgae(false, RobotPosition.ALGAE34),
            OI::isCoralModeOn));
    Driver_ButtonY.onTrue(
        new ButtonCommand(
            new PrepareShootCoral(RobotPosition.L4),
            new ManipulateAlgae(false, RobotPosition.BARGE),
            OI::isCoralModeOn));

    Driver_LeftTrigger.onTrue(new SetCameraSide(CAMERA_SIDE.LEFT));
    Driver_LeftTrigger.onTrue(new SetCameraSide(CAMERA_SIDE.RIGHT));

    Driver_Dpad_North.onTrue(new SetClimbState(ClimbState.CLIMB));
    Driver_Dpad_South.onTrue(new SetClimbState(ClimbState.STOW));

    Driver_Dpad_NorthWest.onTrue(new SetCoralMode(false));
    Driver_Dpad_West.onTrue(new SetCoralMode(false));
    Driver_Dpad_SouthWest.onTrue(new SetCoralMode(false));

    Driver_Dpad_NorthEast.onTrue(new SetCoralMode(true));
    Driver_Dpad_East.onTrue(new SetCoralMode(true));
    Driver_Dpad_SouthEast.onTrue(new SetCoralMode(true));

    Driver_ButtonBack.onTrue(new Park());

    Driver_ButtonStart.onTrue(
        () -> {
          Elevator.getInstance().setTargetHeight(RobotPosition.L4);
          Arm.getInstance().setMotorTargetDegrees(-80);
        });
  }

  public static void configureOperatorControls() {
    Operator_ButtonY.onTrue(new TilterOffset(true));
    Operator_ButtonA.onTrue(new TilterOffset(false));
    Operator_ButtonX.onTrue(() -> Elevator.hasOverridedLimitSwitches = true);
    Operator_ButtonBack.onTrue(new Idle());
  }

  public static void configureBindings() {
    // this line must be before anything else
    configureJoysticks(driver, operator);

    // driver controls

    configureDriverControls();

    // operator controls

    configureOperatorControls();

    // drivetrain config

    mDrivetrain.setDefaultCommand(
        new InstantCommand(
            () -> mDrivetrain.setControl(
                DrivetrainConstants.drive
                    .withVelocityX(-driverUtils.getLeftStickY() * DrivetrainConstants.MaxSpeed)
                    // Drive
                    // forward
                    // with
                    // negative Y (forward)
                    .withVelocityY(-driverUtils.getLeftStickX() * DrivetrainConstants.MaxSpeed)
                    // Drive
                    // left
                    // with
                    // negative
                    // X
                    // (left)
                    .withRotationalRate(
                        -driverUtils.getRightStickX() * DrivetrainConstants.MaxAngularRate)),
            mDrivetrain));
  }

  public static XboxController getOperatorController() {
    return operator;
  }

  public static XboxController getDriverController() {
    return driver;
  }
}

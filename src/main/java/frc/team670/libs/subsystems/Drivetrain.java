package frc.team670.libs.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team670.robot.constants.DriveConstants;

public class Drivetrain extends SwerveDrivetrain<TalonFX, TalonFX, CANcoder> implements Subsystem{
    public static Drivetrain mInstance = new Drivetrain();

    public static Drivetrain getInstance() {
        return mInstance;
    }
    
    private Drivetrain() {
      super(TalonFX::new, TalonFX::new, CANcoder::new, DriveConstants.drivetrainConstants, DriveConstants.moduleConstants);
    }





}

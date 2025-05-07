package frc.team670.robot.commands;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveModule.ModuleRequest;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team670.libs.IO.ControllerUtils;
import frc.team670.robot.subsystems.Drivetrain;

public class Park extends Command {
    Drivetrain mDrivetrain = Drivetrain.getInstance();

    public Park() {
        addRequirements(mDrivetrain);
    }

    @Override
    public void initialize() {
        SwerveModule<TalonFX, TalonFX, CANcoder>[] modules = mDrivetrain.getModules();

        SwerveRequest.Idle idle = new SwerveRequest.Idle();

        mDrivetrain.setControl(idle);

        SwerveModule.ModuleRequest[] requests = {
                new ModuleRequest().withState(new SwerveModuleState(0, Rotation2d.fromDegrees(45))),
                new ModuleRequest().withState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45))),
                new ModuleRequest().withState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45))),
                new ModuleRequest().withState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)))
        };

        int i = 0;
        for (SwerveModule<TalonFX, TalonFX, CANcoder> module : modules) {
            module.apply(requests[i]);
            i++;
        }
    }

    @Override
    public boolean isFinished() {
        // 0 for driver
        return ControllerUtils.joysticMoved(0);
    }
}

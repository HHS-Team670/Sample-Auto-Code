package frc.robot.commands;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import swervelib.SwerveController;
import swervelib.SwerveDrive;
import swervelib.SwerveDriveTest;
import swervelib.math.SwerveMath;
import swervelib.parser.SwerveControllerConfiguration;
import swervelib.parser.SwerveDriveConfiguration;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class YagslAutos {
    public static class AutoDrive extends Command{
        @Override
        public void initialize() {
        
        }

        @Override
        public boolean isFinished() {
            return false;
        }


    }
}

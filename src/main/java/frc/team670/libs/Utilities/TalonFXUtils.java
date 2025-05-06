package frc.team670.libs.Utilities;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TalonFXUtils {

    public static void holdMotor(TalonFX motor) {
        motor.setControl(new MotionMagicVoltage(0).withPosition(motor.getPosition().getValueAsDouble()));
    }

    public static TalonFXConfiguration getConfig(TalonFX motor) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        motor.getConfigurator().refresh(config);
        return config;
    }

    public static boolean isHealthy(TalonFX motor) {
        return (motor != null && motor.isAlive());
    }

    public static TalonFX construct(int motorID, TalonFXConfiguration config) {
        TalonFX motor = new TalonFX(motorID);
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 30; ++i) {
            status = motor.getConfigurator().apply(config);
            if (status.isOK()) {
                break;
            }
        }
        if (!status.isOK()) {
            ConsoleLogger.consoleError("Motor configuration failed: " + status + "\n Motor ID:" + motorID);
        }

        motor.setNeutralMode(NeutralModeValue.Brake);
        return motor;
    }

}

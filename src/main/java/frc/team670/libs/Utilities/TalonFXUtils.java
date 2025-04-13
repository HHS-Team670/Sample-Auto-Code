package frc.team670.libs.Utilities;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;


public class TalonFXUtils {

    public static void holdMotor(TalonFX motor){
        motor.setControl(new MotionMagicVoltage(0).withPosition(motor.getPosition().getValueAsDouble()));
    }

    public static TalonFXConfiguration getConfig(TalonFX motor){
        TalonFXConfiguration config = new TalonFXConfiguration();
        motor.getConfigurator().refresh(config);
        return config;
    }

    public static boolean isHealthy(TalonFX motor){
        return (motor != null && motor.isAlive());
    }



}

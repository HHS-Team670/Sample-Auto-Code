package frc.team670.libs.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;


import java.util.List;

public interface MotorizedSubsytem {
  public List<TalonFX> getMotors();
}

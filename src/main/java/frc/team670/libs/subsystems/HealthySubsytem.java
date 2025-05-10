package frc.team670.libs.subsystems;

import frc.team670.libs.Health.Health;

public interface HealthySubsytem {
  /**
   * The function `checkHealth` in Java is used to perform a health check and
   * returns a `Health` state
   *
   * 
   * @return A public method named `checkHealth` that returns an enum of type
   *         `Health`.
   */
  public Health checkHealth();
}

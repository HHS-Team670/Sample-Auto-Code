package frc.team670.robot.constants;

public enum RobotPosition {
  STOW(0, -90, -64.26269),

  L1(0.84, 220.393, -46.766),
  L2(0.5, -86.047396, 78.93457),

  L3(0, 39, -85),

  L4(1.629, 36.32590659340659, -92),

  ALGAE23(0.637, -44.51, 86.13),

  ALGAE34(0, 28.199, 27),

  PROCESSOR(0.186, 202.113, 96.123),

  BARGE(1.775, 61.749, 89.211),

  STATION(0.83782, 208.236, -64.26269),
  ;

  double armPos;
  double elevatorPos;
  double tilterPos;

  public static RobotPosition currentRobotPos = STOW;

  private RobotPosition(double elevatorHeight, double armAngle, double tilterAngle) {
    this.armPos = armAngle;
    this.elevatorPos = elevatorHeight;
    this.tilterPos = tilterAngle;
  }

  public double getArmAngle() {
    return armPos;
  }

  public double getElevatorHeight() {
    return elevatorPos;
  }

  public double getTilterAngle() {
    return tilterPos;
  }
}

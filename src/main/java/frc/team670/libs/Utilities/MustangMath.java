package frc.team670.libs.Utilities;

public class MustangMath {
    
    public static double getRotationsFromDegrees(double gearRatio, double theta){
        return (theta / 360) * gearRatio;
    }

    public static double getDegreesFromRotations(double gearRatio, double rotations){
        return (rotations * 360) / gearRatio;
    }

    public static boolean doublesEqual(double double1, double double2){
        return doublesEqual(double1, double2, 0.0001);
    }

    public static boolean doublesEqual(double double1, double double2, double errorMargin){
        return Math.abs(double1-double2) <= errorMargin;
    }
}

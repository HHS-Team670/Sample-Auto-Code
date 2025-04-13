package frc.team670.libs.Utilities;

public class MustangMath {
    
    public static double getRotationsFromDegrees(double gearRatio, double theta){
        return (theta / 360) * gearRatio;
    }

    public static double getDegreesFromRotations(double gearRatio, double rotations){
        return (rotations * 360) / gearRatio;
    }

    public static boolean doubleEqual(double double1, double double2){
        return doubleEqual(double1, double2, 0.0001);
    }

    public static boolean doubleEqual(double double1, double double2, double errorMargin){
        return Math.abs(double1-double2) <= errorMargin;
    }
}

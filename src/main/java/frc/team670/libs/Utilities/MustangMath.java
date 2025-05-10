package frc.team670.libs.Utilities;

public class MustangMath {

  /**
   * The function calculates the number of rotations based on a given gear ratio
   * and angle in degrees.
   * 
   * @param gearRatio The gear ratio is a value that represents the ratio of the
   *                  number of teeth on two
   *                  gears that are meshed or two sprockets connected by a chain.
   *                  It indicates how many times one gear
   *                  or sprocket will rotate compared to the other.
   * @param theta     The `theta` parameter represents the angle in degrees that
   *                  you want to convert to
   *                  rotations based on a given gear ratio.
   * @return The method `getRotationsFromDegrees` returns the number of rotations
   *         based on the given
   *         gear ratio and angle in degrees.
   */
  public static double getRotationsFromDegrees(double gearRatio, double theta) {
    return (theta / 360) * gearRatio;
  }

  /**
   * The function calculates the degrees rotated based on a given gear ratio and
   * number of rotations.
   * 
   * @param gearRatio The gear ratio is a measure of the relationship between the
   *                  numbers of teeth on
   *                  two gears that are meshed or two sprockets connected with a
   *                  common roller chain, or the
   *                  circumferences of two pulleys connected with a drive belt.
   *                  It is typically represented as a
   *                  decimal number, such as
   * @param rotations Rotations typically refer to the number of complete
   *                  revolutions made by a
   *                  rotating object. It is a unit of angular measurement.
   * @return The method is returning the number of degrees based on the given gear
   *         ratio and number of
   *         rotations.
   */
  public static double getDegreesFromRotations(double gearRatio, double rotations) {
    return (rotations * 360) / gearRatio;
  }

  /**
   * The function `doublesEqual` compares two double values with a default
   * tolerance of 0.0001.
   * 
   * @param double1 The `doublesEqual` method you provided seems to be a helper
   *                method for comparing
   *                two double values with a given tolerance. The third parameter
   *                in the method call
   *                `doublesEqual(double1, double2, 0.0001)` is the tolerance
   *                value, which is set to 0.
   * @param double2 The `doublesEqual` method you provided seems to be a helper
   *                method for comparing
   *                two double values with a given tolerance. The third parameter
   *                in the method call
   *                `doublesEqual(double1, double2, 0.0001)` is the tolerance
   *                value, which is set to `0.
   * @return The method `doublesEqual` is being called with the parameters
   *         `double1`, `double2`, and
   *         `0.0001`, and the result of this method call is being returned.
   */
  public static boolean doublesEqual(double double1, double double2) {
    return doublesEqual(double1, double2, 0.0001);
  }

  /**
   * The function `doublesEqual` checks if two double values are equal within a
   * specified error margin.
   * 
   * @param double1     The `double1` parameter is the first double value that you
   *                    want to compare for
   *                    equality with another double value.
   * @param double2     The `double2` parameter in the `doublesEqual` method
   *                    represents the second double
   *                    value that you want to compare with `double1` within a
   *                    specified error margin.
   * @param errorMargin The `errorMargin` parameter in the `doublesEqual` method
   *                    represents the maximum
   *                    allowable difference between the two double values
   *                    `double1` and `double2` for them to be considered
   *                    equal. If the absolute difference between `double1` and
   *                    `double2` is less than or equal to
   * @return The method `doublesEqual` returns a boolean value indicating whether
   *         the difference between
   *         `double1` and `double2` is less than or equal to the specified
   *         `errorMargin`.
   */
  public static boolean doublesEqual(double double1, double double2, double errorMargin) {
    return Math.abs(double1 - double2) <= errorMargin;
  }

  /**
   * The function calculates the distance traveled in meters based on the
   * circumference of the
   * sprocket, gear ratio, and number of rotations.
   * 
   * @param circumferenceSprocket The `circumferenceSprocket` parameter represents
   *                              the circumference of
   *                              the sprocket in meters. This value is used in
   *                              the calculation to convert rotations into meters
   *                              traveled based on the gear ratio and number of
   *                              rotations.
   * @param gearRatio             The gear ratio represents the ratio of the
   *                              number of teeth on the chainring to
   *                              the number of teeth on the rear sprocket. It is
   *                              used to calculate how many times the rear wheel
   *                              rotates for each rotation of the pedals.
   * @param rotations             Rotations typically refer to the number of times
   *                              a wheel or sprocket turns
   *                              completely around. If you have a specific value
   *                              for rotations that you would like to convert
   *                              into
   *                              meters, you can provide that value as an
   *                              argument when calling the
   *                              `getMetersFromRotations`
   *                              method.
   * @return The method is returning the distance in meters traveled based on the
   *         given circumference
   *         of the sprocket, gear ratio, and number of rotations.
   */
  public static double getMetersFromRotations(
      double circumferenceSprocket, double gearRatio, double rotations) {
    return (rotations / gearRatio) * circumferenceSprocket;
  }

  /**
   * The function calculates the number of rotations based on the sprocket
   * circumference, gear ratio,
   * and distance in meters.
   * 
   * @param circumferenceSprocket The `circumferenceSprocket` parameter represents
   *                              the circumference of
   *                              the sprocket in meters. This value is used in
   *                              the calculation to convert the distance traveled
   *                              in
   *                              meters to the number of rotations made by the
   *                              sprocket.
   * @param gearRatio             The gear ratio represents the ratio of the
   *                              number of teeth on the chainring to
   *                              the number of teeth on the rear sprocket. It is
   *                              used to calculate how many times the rear wheel
   *                              rotates for each rotation of the pedals.
   * @param meters                The `meters` parameter represents the distance
   *                              traveled by a bicycle in meters.
   * @return The method is returning the number of rotations based on the given
   *         parameters:
   *         circumference of the sprocket, gear ratio, and distance in meters.
   */
  public static double getRotationsFromMeters(
      double circumferenceSprocket, double gearRatio, double meters) {
    return (meters / circumferenceSprocket) * gearRatio;
  }
}

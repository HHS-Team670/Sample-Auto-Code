package frc.team670.libs.Utilities;

public class TypeUtils {
  /**
   * The function `getStaticField` retrieves the value of a static field from a given class using
   * reflection in Java.
   *
   * @param classStorage The `classStorage` parameter is the Class object that represents the class
   *     containing the static field you want to retrieve.
   * @param name The `name` parameter in the `getStaticField` method represents the name of the
   *     static field that you want to retrieve from the specified class.
   * @return The method `getStaticField` is returning a static field value of type `T` from the
   *     specified class `classStorage` with the given field name `name`. The return type is `T`,
   *     which means it can be any type specified when calling the method.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getStaticField(Class<?> classStorage, String name) {
    try {
      return (T) classStorage.getField(name).get(null);
    } catch (IllegalArgumentException
        | IllegalAccessException
        | NoSuchFieldException
        | SecurityException e) {
      e.printStackTrace();
    }
    return null;
  }
}

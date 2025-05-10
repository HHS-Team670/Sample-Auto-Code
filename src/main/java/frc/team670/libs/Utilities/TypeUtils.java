package frc.team670.libs.Utilities;

import java.util.List;

public class TypeUtils {
  /**
   * The function `getStaticField` retrieves the value of a static field from a
   * given class using
   * reflection in Java.
   *
   * @param classStorage The `classStorage` parameter is the Class object that
   *                     represents the class
   *                     containing the static field you want to retrieve.
   * @param name         The `name` parameter in the `getStaticField` method
   *                     represents the name of the
   *                     static field that you want to retrieve from the specified
   *                     class.
   * @return The method `getStaticField` is returning a static field value of type
   *         `T` from the
   *         specified class `classStorage` with the given field name `name`. The
   *         return type is `T`,
   *         which means it can be any type specified when calling the method.
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

  public static <T> void setStaticField(Class<?> classStorage, String name, T value) {
    try {
      classStorage.getField(name).setAccessible(true);
      classStorage.getField(name).set(null, value);
    } catch (IllegalArgumentException
        | IllegalAccessException
        | NoSuchFieldException
        | SecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * The `unimplemented` function logs a warning message with caller information
   * when called and
   * returns null.
   * 
   * This can be used to ensure that a unimplemented or unused method is not being
   * called
   * 
   * @return The `unimplemented()` method is returning `null`.
   */
  public static <T> T unimplemented() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    // Index 0 is getStackTrace, 1 is unimplemented(), 2 is the caller
    if (stackTrace.length > 2) {
      StackTraceElement caller = stackTrace[2];
      String info = String.format(
          "Warning: attempted to use unimplemented method at %s.%s(%s:%d)",
          caller.getClassName(),
          caller.getMethodName(),
          caller.getFileName(),
          caller.getLineNumber());
      ConsoleLogger.consoleWarning(info);
    }
    return null;
  }

  /**
   * The function `addLists` takes two lists of the same type and appends the
   * elements of the second
   * list to the first list.
   * 
   * @param list1 List<T> - the first list to which elements from the second list
   *              will be added
   * @param list2 List<T>
   * @return The method `addLists` returns a `List<T>` which is the result of
   *         adding all elements from
   *         `list2` to `list1`.
   */
  public static <T> List<T> addLists(List<T> list1, List<T> list2) {
    for (T t : list2) {
      list1.add(t);
    }
    return list1;
  }
}

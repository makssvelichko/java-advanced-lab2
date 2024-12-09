package annotations;

import java.lang.annotation.*;
/**
 * Annotation to specify the database table associated with a class.
 * Used to map a class to a specific table in the database.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
  /**
   * Specifies the name of the database table.
   *
   * @return the table name
   */
  String name();
}

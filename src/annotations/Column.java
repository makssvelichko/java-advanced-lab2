package annotations;

import java.lang.annotation.*;
/**
 * Annotation to mark a class field as a database column.
 * Used to specify the column name corresponding to the field.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
  /**
   * Specifies the name of the database column associated with the field.
   *
   * @return the column name
   */
  String name();
}

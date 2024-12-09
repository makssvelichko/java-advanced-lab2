import annotations.Column;
import annotations.Id;
import annotations.Table;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Arrays;
/**
 * A utility class to generate SQL queries (CREATE, INSERT, SELECT, UPDATE, DELETE)
 * using reflection and custom annotations.
 */
public class SQLGenerator {
  /**
   * Generates a SQL {@code CREATE TABLE} statement for the specified class.
   *
   * @param clazz the class representing the table structure
   * @return a SQL {@code CREATE TABLE} statement as a {@code String}
   * @throws IllegalArgumentException if the class is not annotated with {@code @Table}
   */
  public static String generateCreateQuery(Class<?> clazz) {
    Table table = getTableAnnotation(clazz);
    String tableName = table.name();
    String columns = Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(Column.class))
        .map(field -> {
          Column column = field.getAnnotation(Column.class);
          return column.name() + " " + getSQLType(field.getType());
        })
        .collect(Collectors.joining(", "));
    return String.format("CREATE TABLE %s (%s);", tableName, columns);
  }
  /**
   * Generates a SQL {@code INSERT INTO} statement for the specified object.
   *
   * @param obj the object representing the data to insert
   * @return a SQL {@code INSERT INTO} statement as a {@code String}
   * @throws RuntimeException if there is an error accessing field values
   * @throws IllegalArgumentException if the class is not annotated with {@code @Table}
   */
  public static String generateInsertQuery(Object obj) {
    Class<?> clazz = obj.getClass();
    Table table = getTableAnnotation(clazz);
    String tableName = table.name();

    Field[] fields = clazz.getDeclaredFields();
    String columns = Arrays.stream(fields)
        .filter(field -> field.isAnnotationPresent(Column.class))
        .map(field -> field.getAnnotation(Column.class).name())
        .collect(Collectors.joining(", "));

    String values = Arrays.stream(fields)
        .filter(field -> field.isAnnotationPresent(Column.class))
        .map(field -> {
          try {
            field.setAccessible(true);
            Object value = field.get(obj);
            return value instanceof String ? "'" + value + "'" : String.valueOf(value);
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.joining(", "));

    return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, values);
  }
  /**
   * Generates a SQL {@code SELECT} statement for the specified class.
   *
   * @param clazz the class representing the table structure
   * @return a SQL {@code SELECT} statement as a {@code String}
   * @throws IllegalArgumentException if the class is not annotated with {@code @Table}
   */
  public static String generateSelectQuery(Class<?> clazz) {
    Table table = getTableAnnotation(clazz);
    String tableName = table.name();

    String columns = Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(Column.class))
        .map(field -> field.getAnnotation(Column.class).name())
        .collect(Collectors.joining(", "));

    return String.format("SELECT %s FROM %s;", columns, tableName);
  }
  /**
   * Generates a SQL {@code UPDATE} statement for the specified object.
   *
   * @param obj the object representing the data to update
   * @return a SQL {@code UPDATE} statement as a {@code String}
   * @throws RuntimeException if there is an error accessing field values
   * @throws IllegalArgumentException if the class is not annotated with {@code @Table}
   */
  public static String generateUpdateQuery(Object obj) {
    Class<?> clazz = obj.getClass();
    Table table = getTableAnnotation(clazz);
    String tableName = table.name();

    Field[] fields = clazz.getDeclaredFields();
    String setClause = Arrays.stream(fields)
        .filter(field -> field.isAnnotationPresent(Column.class))
        .map(field -> {
          try {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (field.isAnnotationPresent(Id.class)) {
              return null;
            }
            Object value = field.get(obj);
            String columnValue = value instanceof String ? "'" + value + "'" : String.valueOf(value);
            return column.name() + " = " + columnValue;
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.joining(", "));

    Field idField = clazz.getDeclaredFields()[0];
    idField.setAccessible(true);
    Object idValue;
    try {
      idValue = idField.get(obj);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    return String.format("UPDATE %s SET %s WHERE %s = %s;", tableName, setClause,
        idField.getAnnotation(Column.class).name(),
        idValue instanceof String ? "'" + idValue + "'" : idValue);
  }
  /**
   * Generates a SQL {@code DELETE} statement for the specified class and ID.
   *
   * @param clazz the class representing the table structure
   * @param id    the ID value to delete
   * @return a SQL {@code DELETE} statement as a {@code String}
   * @throws IllegalArgumentException if the class is not annotated with {@code @Table}
   */
  public static String generateDeleteQuery(Class<?> clazz, Object id) {
    Table table = getTableAnnotation(clazz);
    String tableName = table.name();

    Field idField = clazz.getDeclaredFields()[0];
    Column idColumn = idField.getAnnotation(Column.class);

    return String.format("DELETE FROM %s WHERE %s = %s;", tableName, idColumn.name(),
        id instanceof String ? "'" + id + "'" : id);
  }
  /**
   * Determines the SQL type for a given Java field type.
   *
   * @param fieldType the {@code Class} representing the field type
   * @return a {@code String} representing the corresponding SQL type
   */
  private static String getSQLType(Class<?> fieldType) {
    if (fieldType == String.class) return "VARCHAR(255)";
    if (fieldType == int.class || fieldType == Integer.class) return "INT";
    if (fieldType == double.class || fieldType == Double.class) return "DOUBLE";
    return "TEXT";
  }
  /**
   * Retrieves the {@code Table} annotation from the class.
   *
   * @param clazz the class to inspect
   * @return the {@code Table} annotation
   * @throws IllegalArgumentException if the class is not annotated with {@code @Table}
   */
  private static Table getTableAnnotation(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(Table.class)) {
      throw new IllegalArgumentException("Class must be annotated with @annotations.Table");
    }
    return clazz.getAnnotation(Table.class);
  }
}

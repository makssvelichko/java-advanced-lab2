import annotations.Column;
import annotations.Id;
import annotations.Table;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Arrays;

public class SQLGenerator {

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

  public static String generateSelectQuery(Class<?> clazz) {
    Table table = getTableAnnotation(clazz);
    String tableName = table.name();

    String columns = Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(Column.class))
        .map(field -> field.getAnnotation(Column.class).name())
        .collect(Collectors.joining(", "));

    return String.format("SELECT %s FROM %s;", columns, tableName);
  }

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

  public static String generateDeleteQuery(Class<?> clazz, Object id) {
    Table table = getTableAnnotation(clazz);
    String tableName = table.name();

    Field idField = clazz.getDeclaredFields()[0];
    Column idColumn = idField.getAnnotation(Column.class);

    return String.format("DELETE FROM %s WHERE %s = %s;", tableName, idColumn.name(),
        id instanceof String ? "'" + id + "'" : id);
  }

  private static String getSQLType(Class<?> fieldType) {
    if (fieldType == String.class) return "VARCHAR(255)";
    if (fieldType == int.class || fieldType == Integer.class) return "INT";
    if (fieldType == double.class || fieldType == Double.class) return "DOUBLE";
    return "TEXT";
  }

  private static Table getTableAnnotation(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(Table.class)) {
      throw new IllegalArgumentException("Class must be annotated with @annotations.Table");
    }
    return clazz.getAnnotation(Table.class);
  }
}

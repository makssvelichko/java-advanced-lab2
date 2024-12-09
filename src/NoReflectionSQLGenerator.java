import java.util.Map;

class NoReflectionSQLGenerator {

  public static String createTable(String tableName, Map<String, String> columns) {
    String columnDefinitions = columns.entrySet().stream()
        .map(entry -> entry.getKey() + " " + entry.getValue())
        .reduce((col1, col2) -> col1 + ", " + col2)
        .orElseThrow(() -> new IllegalArgumentException("Columns map cannot be empty"));
    return String.format("CREATE TABLE %s (%s);", tableName, columnDefinitions);
  }

  public static String insert(String tableName, Map<String, Object> values) {
    String columns = String.join(", ", values.keySet());
    String valuePlaceholders = values.values().stream()
        .map(value -> value instanceof String ?
            "'" + value + "'" : String.valueOf(value))
        .reduce((val1, val2) -> val1 + ", " + val2)
        .orElse("");
    return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, valuePlaceholders);
  }

  public static String select(String tableName, String... columns) {
    String columnList = String.join(", ", columns);
    return String.format("SELECT %s FROM %s;", columnList, tableName);
  }

  public static String update(String tableName, Map<String, Object> values, String condition) {
    String setClause = values.entrySet().stream()
        .map(entry -> {
          String value = entry.getValue() instanceof String ?
              "'" + entry.getValue() + "'" : String.valueOf(entry.getValue());
          return entry.getKey() + " = " + value;
        })
        .reduce((set1, set2) -> set1 + ", " + set2)
        .orElse("");
    return String.format("UPDATE %s SET %s WHERE %s;", tableName, setClause, condition);
  }

  public static String delete(String tableName, String condition) {
    return String.format("DELETE FROM %s WHERE %s;", tableName, condition);
  }
}

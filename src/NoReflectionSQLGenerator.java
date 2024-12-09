import java.util.Map;
/**
 * A utility class to generate basic SQL statements (CREATE, INSERT, SELECT, UPDATE, DELETE)
 * without using reflection.
 */
class NoReflectionSQLGenerator {
  /**
   * Generates a SQL statement to create a table with the specified columns.
   *
   * @param tableName the name of the table to create
   * @param columns   a map where keys are column names and values are column types (e.g., "VARCHAR(255)")
   * @return a SQL {@code CREATE TABLE} statement as a {@code String}
   * @throws IllegalArgumentException if the columns map is empty
   */
  public static String createTable(String tableName, Map<String, String> columns) {
    String columnDefinitions = columns.entrySet().stream()
        .map(entry -> entry.getKey() + " " + entry.getValue())
        .reduce((col1, col2) -> col1 + ", " + col2)
        .orElseThrow(() -> new IllegalArgumentException("Columns map cannot be empty"));
    return String.format("CREATE TABLE %s (%s);", tableName, columnDefinitions);
  }
  /**
   * Generates a SQL statement to insert values into the specified table.
   *
   * @param tableName the name of the table
   * @param values    a map where keys are column names and values are the corresponding values to insert
   * @return a SQL {@code INSERT INTO} statement as a {@code String}
   */
  public static String insert(String tableName, Map<String, Object> values) {
    String columns = String.join(", ", values.keySet());
    String valuePlaceholders = values.values().stream()
        .map(value -> value instanceof String ?
            "'" + value + "'" : String.valueOf(value))
        .reduce((val1, val2) -> val1 + ", " + val2)
        .orElse("");
    return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, valuePlaceholders);
  }
  /**
   * Generates a SQL statement to select specified columns from the given table.
   *
   * @param tableName the name of the table
   * @param columns   the columns to select (if none provided, defaults to "*")
   * @return a SQL {@code SELECT} statement as a {@code String}
   */
  public static String select(String tableName, String... columns) {
    String columnList = String.join(", ", columns);
    return String.format("SELECT %s FROM %s;", columnList, tableName);
  }
  /**
   * Generates a SQL statement to update values in the specified table with a given condition.
   *
   * @param tableName the name of the table
   * @param values    a map where keys are column names and values are the new values to set
   * @param condition the condition to apply for the update (e.g., "id = 1")
   * @return a SQL {@code UPDATE} statement as a {@code String}
   */
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
  /**
   * Generates a SQL statement to delete rows from the specified table with a given condition.
   *
   * @param tableName the name of the table
   * @param condition the condition to apply for the deletion (e.g., "id = 1")
   * @return a SQL {@code DELETE} statement as a {@code String}
   */
  public static String delete(String tableName, String condition) {
    return String.format("DELETE FROM %s WHERE %s;", tableName, condition);
  }
}

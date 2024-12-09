import examples.Car;
import examples.Message;
import examples.Student;
import java.util.Map;
/**
 * The {@code Main} class demonstrates the usage of both reflection-based and no-reflection-based
 * SQL generators by executing various SQL generation methods and printing the results.
 */
public class Main {
  /**
   * Main method which demonstrates the generation of SQL queries.
   * It compares the performance and output of reflection-based and no-reflection-based SQL generators.
   *
   * @param args Command-line arguments (not used in this example).
   */
  public static void main(String[] args) {

    // Creating an example Car object
    Car car = new Car(1, "Toyota", 25000.00);

    // Reflection-based SQL generator
    long startTime = System.currentTimeMillis();
    System.out.println(SQLGenerator.generateCreateQuery(Car.class));
    System.out.println(SQLGenerator.generateInsertQuery(car));
    System.out.println(SQLGenerator.generateSelectQuery(Message.class));
    // Modifying car object and generating UPDATE query
    car.setModel("Honda");
    car.setPrice(27000.00);
    System.out.println(SQLGenerator.generateUpdateQuery(car));
    System.out.println(SQLGenerator.generateDeleteQuery(Student.class, 1));
    long endTime = System.currentTimeMillis();
    long reflectionDuration = endTime - startTime;
    System.out.println("Reflection-based SQL generator duration: " + reflectionDuration + "ms");

    // Generating SQL queries using no-reflection-based approach
    startTime = System.currentTimeMillis();
    String createCarTable = NoReflectionSQLGenerator.createTable(
        "cars",
        Map.of(
            "id", "INT",
            "mark", "VARCHAR(255)",
            "price", "DOUBLE"
        )
    );
    System.out.println(createCarTable);

    String insertCar = NoReflectionSQLGenerator.insert(
        "cars",
        Map.of(
            "id", 1,
            "mark", "Toyota",
            "price", 25000.00
        )
    );
    System.out.println(insertCar);

    String selectCar = NoReflectionSQLGenerator.select(
        "messages", "id", "content", "sender", "receiver");
    System.out.println(selectCar);

    String updateCar = NoReflectionSQLGenerator.update(
        "cars",
        Map.of(
            "mark", "Honda",
            "price", 27000.00
        ),
        "id = 1"
    );
    System.out.println(updateCar);

    String deleteCar = NoReflectionSQLGenerator.delete("students", "id = 1");
    System.out.println(deleteCar);

    // Calculating and displaying the no-reflection-based SQL generator duration
    endTime = System.currentTimeMillis();
    long noReflectionDuration = endTime - startTime;
    System.out.println("No-reflection-based SQL generator duration: " + noReflectionDuration + "ms");
  }
}

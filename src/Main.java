import examples.Car;
import examples.Message;
import examples.Student;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    Car car = new Car(1, "Toyota", 25000.00);

    // Reflection-based SQL generator
    long startTime = System.currentTimeMillis();
    System.out.println(SQLGenerator.generateCreateQuery(Car.class));
    System.out.println(SQLGenerator.generateInsertQuery(car));
    System.out.println(SQLGenerator.generateSelectQuery(Message.class));
    car.setModel("Honda");
    car.setPrice(27000.00);
    System.out.println(SQLGenerator.generateUpdateQuery(car));
    System.out.println(SQLGenerator.generateDeleteQuery(Student.class, 1));
    long endTime = System.currentTimeMillis();
    long reflectionDuration = endTime - startTime;
    System.out.println("Reflection-based SQL generator duration: " + reflectionDuration + "ms");

    // No-reflection-based SQL generator
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
    endTime = System.currentTimeMillis();
    long noReflectionDuration = endTime - startTime;
    System.out.println("No-reflection-based SQL generator duration: " + noReflectionDuration + "ms");
  }
}

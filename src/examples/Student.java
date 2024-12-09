package examples;

import annotations.Column;
import annotations.Id;
import annotations.Table;
/**
 * Represents a student entity mapped to the "students" table in the database.
 */
@Table(name = "students")
public class Student {
  /**
   * The unique identifier for the student.
   */
  @Id
  @Column(name = "id")
  private int id;
  /**
   * The name of the student.
   */
  @Column(name = "name")
  private String name;
  /**
   * The email of the student.
   */
  @Column(name = "email")
  private String email;
  /**
   * The age of the student.
   */
  @Column(name = "age")
  private int age;
  /**
   * Constructs a new {@code Student} with the specified id, name, email, and age.
   *
   * @param id    the unique identifier for the student
   * @param name  the name of the student
   * @param email the email of the student
   * @param age   the age of the student
   */
  public Student(int id, String name, String email, int age) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.age = age;
  }
  /**
   * Constructs a new {@code Student} with the specified name, email, and age.
   *
   * @param name  the name of the student
   * @param email the email of the student
   * @param age   the age of the student
   */
  public Student(String name, String email, int age) {
    this.name = name;
    this.email = email;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}

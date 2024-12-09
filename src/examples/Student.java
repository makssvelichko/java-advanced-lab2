package examples;

import annotations.Column;
import annotations.Id;
import annotations.Table;

@Table(name = "students")
public class Student {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "age")
  private int age;

  public Student(int id, String name, String email, int age) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.age = age;
  }

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

package examples;

import annotations.Column;
import annotations.Id;
import annotations.Table;
/**
 * Represents a car entity mapped to the "cars" table in the database.
 */
@Table(name = "cars")
public class Car {
  /**
   * The unique identifier for the car.
   */
  @Id
  @Column(name = "id")
  private int id;

  /**
   * The model of the car.
   */
  @Column(name = "model")
  private String model;
  /**
   * The price of the car.
   */
  @Column(name = "price")
  private double price;
  /**
   * Constructs a new {@code Car} with the specified id, model, and price.
   *
   * @param id    the unique identifier for the car
   * @param model the model of the car
   * @param price the price of the car
   */
  public Car(int id, String model, double price) {
    this.id = id;
    this.model = model;
    this.price = price;
  }
  /**
   * Constructs a new {@code Car} with the specified model, and price.
   *
   * @param model the model of the car
   * @param price the price of the car
   */
  public Car(String model, double price) {
    this.model = model;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}

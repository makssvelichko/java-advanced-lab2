package examples;

import annotations.Column;
import annotations.Id;
import annotations.Table;

@Table(name = "cars")
public class Car {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "model")
  private String model;

  @Column(name = "price")
  private double price;

  public Car(int id, String model, double price) {
    this.id = id;
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

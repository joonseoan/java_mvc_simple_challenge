package mvc_challenge.superstore;

import java.io.Serializable;

public class Item implements Serializable {
  private String name;
  private Double price;
  private Double discount;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  // [IMPORTANT] Double can be converted into String in the client
  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getDiscount() {
    return discount;
  }

  // [IMPORTANT] Double can be converted into String in the client
  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  @Override
  public String toString() {
    return "Item{" +
            "name='" + name + '\'' +
            ", price=" + price +
            ", discount=" + discount +
            '}';
  }
}

package mvc_challenge.superstore;

import java.io.Serializable;

public class Item implements Serializable {
  private String name;
  private String price;
  private String discount;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getDiscount() {
    return discount;
  }

  public void setDiscount(String discount) {
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

package mvc_challenge.superstore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class Item implements Serializable {
  @NotBlank(message = "Name cannot be blank.")
  private String name;
  // Need to check out how to validate the empty value for double. ---> Custom validation?
//  @NotBlank(message = "Price cannot be blank")
  @Min(value = 0, message = "Price cannot be negative.")
  private Double price;
//  @NotBlank(message = "Discount cannot be blank")
  @Min(value = 0, message = "Discount cannot be negative.")
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

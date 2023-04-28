package mvc_challenge.superstore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Order implements Serializable {
  private String id;
  @NotBlank(message = "Please choose a category.")
  private String category;

  // [IMPORTANT!!!]
  // When we need to a child class,
  // we can validate the child class's field with `@Valid`
  @Valid
  private Item item;

  // Need to add validation for null for date.
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Past(message = "Order date must be in the past")
  private Date date;

  public Order(Item item) {
    this.id = UUID.randomUUID().toString();
    this.item =  item;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Date getDate() {
    return date;
  }

  // [IMPORTANT] @DateTimeFormat will parse String type from the client
  // into Date type.
  public void setDate(Date date) {
    this.date = date;
  }

  // [IMPORTANT!!!!]
  // It can be called as `order.formatData` in Thymeleaf.
  // Please find the comments in `inventory.html`
  public String getFormatDate() {
    // Return String type through `SimpleDateFormat`
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(date);
  }

  @Override
  public String toString() {
    return "Orders{" +
            "category='" + category + '\'' +
            ", id='" + id + '\'' +
//            ", item=" + item +
            ", date=" + date +
            '}';
  }
}

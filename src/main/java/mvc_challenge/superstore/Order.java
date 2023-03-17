package mvc_challenge.superstore;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Order implements Serializable {
  private String id;
  private String category;
  private Item item;
  private String date;

  public Order(Item item) {
    this.id = UUID.randomUUID().toString();
    this.item = item;
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

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Orders{" +
            "category='" + category + '\'' +
            ", id='" + id + '\'' +
            ", item=" + item +
            ", date=" + date +
            '}';
  }
}

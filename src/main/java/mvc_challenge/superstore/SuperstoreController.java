package mvc_challenge.superstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class SuperstoreController {
  List<Order> orders = new ArrayList<>();

  @GetMapping("/")
  public String getForm(Model model, @RequestParam(required = false) String id) throws IndexOutOfBoundsException {
    Order order;

    int index = getIndex(id);

    if (index > -1) {
      order = orders.get(index);
    } else {
      Item item = new Item();
      order = new Order(item);
    }

    model.addAttribute("categories", Constants.CATEGORIES);
    model.addAttribute("order", order);

    return "form";
  }

  @PostMapping("/handleSubmit")
  public String handleOrderSubmit(Order order) {
    int index = getIndex(order.getId());

    if (index > -1) {
      orders.set(index, order);
    } else {
      orders.add(order);
    }

    return "redirect:/inventory";
  }

  @GetMapping("/inventory")
  public String getInventory(Model model) {
    model.addAttribute("orders", orders);

    return "inventory";
  }

  private int getIndex(String id) throws NullPointerException {
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getId().equals(id)) {
        return i;
      }
    }

    return -1;
  }
}

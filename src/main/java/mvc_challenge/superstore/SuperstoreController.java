package mvc_challenge.superstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SuperstoreController {
  List<Order> orders = new ArrayList<>();

  @GetMapping("/")
  public String getForm(Model model) {
    Item item = new Item();
    Order order = new Order(item);

    model.addAttribute("categories", Constants.CATEGORIES);
//    model.addAttribute("item", item);
    model.addAttribute("order", order);

    return "form";
  }

  @PostMapping("/handleSubmit")
  public String handleOrderSubmit(Order order) {
    System.out.println(order);
    return "redirect:/inventory";
  }

  @GetMapping("/inventory")
  public String getInventory(Model model) {
    return "inventory";
  }
}

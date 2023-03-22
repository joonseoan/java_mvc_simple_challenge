package mvc_challenge.superstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class SuperstoreController {
  private List<Order> orders = new ArrayList<>();

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
  public String handleOrderSubmit(Order order, RedirectAttributes redirectAttributes) {
    int index = getIndex(order.getId());
    String flashString;

    if (index > -1) {
      Date newDate = order.getDate();
      Date oldDate = orders.get(index).getDate();

      if (!within5Days(newDate, oldDate)) {
        flashString = Constants.FAIL;

      } else {
        flashString = Constants.SUCCESS;
        orders.set(index, order);
      }

    } else {
      flashString = Constants.SUCCESS;
      orders.add(order);
    }


    // add a flash attribute
    redirectAttributes.addFlashAttribute("status", flashString);

    return "redirect:/inventory";
  }

  @GetMapping("/inventory")
  public String getInventory(Model model) {
    model.addAttribute("orders", orders);

    // [IMPORTANT] flashAttributes with key name, "status" will be automatically added into `model`
    return "inventory";
  }

  private int getIndex(String id) throws NullPointerException {
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getId().equals(id)) {
        return i;
      }
    }

    return Constants.NOT_FOUND;
  }

  private boolean within5Days(Date newDate, Date oldDate) {
    // getTime() with milliseconds
    long diff = Math.abs(newDate.getTime() - oldDate.getTime());
    // milliseconds to day
    return TimeUnit.MILLISECONDS.toDays(diff) <= 5;
  }
}

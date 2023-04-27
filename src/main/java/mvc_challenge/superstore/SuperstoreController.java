package mvc_challenge.superstore;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Learn:
 * 1. Double type can be automatically parsed from String of the client input.
 * 2. Date is not automatically parsed from String so that we need to implement
 *    an annotation `@DateTimeFormat(pattern = "yyyy-MM-dd")`
 * 3. The client can directly call a method in a POJO class instance by implementing for example,
 *    `getFormatDate` can be called as `order.formatData` in Thymeleaf.
 *    Please find the comments in `inventory.html`
 * 4. FlashAttribute can be added in `submitHandler` function
 *    to put additional properties in `model.addAttribute`
 * 5. Date getTime() in MiliSeconds to days through TimeUnit.MILLISECONDS.toDays(diff) <= 5
 */

@Controller
public class SuperstoreController {
  private List<Order> orders = new ArrayList<>();

  @GetMapping("/")
  public String getForm(Model model, @RequestParam(required = false) String id) {
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
  // RedirectAttributes: "FlashAttribute: which is a data that survives a `redirect`
  // It is placed as a last parameter.
  public String handleOrderSubmit(
          @Valid
          Order order,
          BindingResult result,
          RedirectAttributes redirectAttributes
  ) {

    System.out.println("category: " + order.getCategory());
    System.out.println("Has Error: " + result.hasErrors());

    if (result.hasErrors()) {
      return "form";
    }

    int index = getIndex(order.getId());
    String flashString = Constants.SUCCESS;

    if (index > -1) {
      Date newDate = order.getDate();
      Date oldDate = orders.get(index).getDate();

      if (!within5Days(newDate, oldDate)) {
        flashString = Constants.FAIL;
      } else {
        orders.set(index, order);
      }
    } else {
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

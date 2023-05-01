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
 * 6. The nested field validation @Valid Item item
 * 7. Cross-validation check for the nested field
 * 8. API validation does not support the validation for @NotBlank of Number and Date types
 *    Will need to provide the custom validation.
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

    // 1) It can be set directly in the HTML file as well.
//    model.addAttribute("categories", Constants.CATEGORIES);
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
    if (order.getItem().getPrice() < order.getItem().getDiscount()) {
      /*
       * bindingResult.rejectValue(arg1, arg2, arg3):
       * - arg1: identifies which field the error is associated with.
       * - arg2: error code which acts a message key for the messages.properties file
       *   (or messages_en.properties or messages_fr.properties etc., if these are being used).
       * - arg3: Error Message
       * */
      // arg2 is not required to be used here.
      // [IMPORTANT]: it is the way to get the nested field.
      result.rejectValue("item.price", "", "Price cannot be less than the discount.");
    }

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

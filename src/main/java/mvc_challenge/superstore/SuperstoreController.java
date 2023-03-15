package mvc_challenge.superstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperstoreController {
  @GetMapping("/")
  public String getForm(Model model) {
    model.addAttribute("categories", Constants.CATEGORIES);
    return "form";
  }

  @GetMapping("/inventory")
  public String getInventory(Model model) {
    return "inventory";
  }
}

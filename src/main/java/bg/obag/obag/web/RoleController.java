package bg.obag.obag.web;

import bg.obag.obag.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change")
    public String getRoles(Model model) {
        model.addAttribute("emails", userService.getAllUsersEmails());
        return "changeRole";
    }

    @PostMapping("/change")
    public String changeRole(@RequestParam String email, @RequestParam String role,
                             RedirectAttributes redirectAttributes) {
        userService.changeRole(email, role);
        redirectAttributes.addFlashAttribute("successfullyChangedRole", true);
        return "redirect:/roles/change";
    }

    @PostMapping("/remove")
    public String removeRole(@RequestParam String email, @RequestParam String role,
                             RedirectAttributes redirectAttributes) {
        userService.removeRole(email, role);
        redirectAttributes.addFlashAttribute("successfullyChangedRole", true);
        return "redirect:/roles/change";
    }
}

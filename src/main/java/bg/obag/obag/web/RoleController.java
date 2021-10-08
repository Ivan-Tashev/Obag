package bg.obag.obag.web;

import bg.obag.obag.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String changeRole(@RequestParam String email,
                             @RequestParam String role) {
        userService.changeRole(email, role);
        return "redirect:/";
    }
}

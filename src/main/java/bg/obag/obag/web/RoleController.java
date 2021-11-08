package bg.obag.obag.web;

import bg.obag.obag.service.RoleService;
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
    private final RoleService roleService;

    public RoleController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/change")
    public String getRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "changeRole";
    }

    @GetMapping("/change/show")
    public String getRolesForEmail(Model model, @RequestParam String email) {
        model.addAttribute("userRoles", userService.findUserRolesByEmail(email))
                .addAttribute("roles", roleService.findAll())
                .addAttribute("email", email);
        return "changeRole";
    }

    @PostMapping("/change")
    public String changeRole(@RequestParam String email, @RequestParam String role,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("email", email);

        userService.changeRole(email, role);
        redirectAttributes.addFlashAttribute("successfullyChangedRole", true);
        return "redirect:/roles/change";
    }

    @PostMapping("/remove")
    public String removeRole(@RequestParam String email, @RequestParam String role,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("email", email);
        userService.removeRole(email, role);
        redirectAttributes.addFlashAttribute("successfullyChangedRole", true);
        return "redirect:/roles/change";
    }
}

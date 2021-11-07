package bg.obag.obag.web;

import bg.obag.obag.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public String getFirstName(Principal principal) {
        return principal == null ? "" :
                userService.findCurrentUserByEmail(principal.getName()).getFirstName();
    }

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

}

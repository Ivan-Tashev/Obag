package bg.obag.obag.web;

import bg.obag.obag.service.UserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {
    private final UserService userService;
    private MeterRegistry meterRegistry;
    private final Counter counter;

    public HomeController(UserService userService, MeterRegistry meterRegistry) {
        this.userService = userService;
        counter = Counter.builder("homepage").description("Homepage non-unique loadings.").register(meterRegistry);
    }

    @ModelAttribute("user")
    public String getFirstName(Principal principal) {
        return principal == null ? "" :
                userService.findCurrentUserByEmail(principal.getName()).getFirstName();
    }

    @GetMapping("/")
    public String getHome() {
        counter.increment();
        return "index";
    }
}

package bg.obag.obag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {

    @GetMapping("/phylosophy")
    public String phylosophy() {
        return "phylosophy";
    }

    @GetMapping("/delivery")
    public String delivery() {
        return "delivery";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/loyalty")
    public String loyalty() {
        return "loyalty";
    }

    @GetMapping("/viber")
    public String viber() {
        return "viber";
    }

    @GetMapping("/conditions")
    public String conditions() {
        return "conditions";
    }

    @GetMapping("/claims")
    public String claims() {
        return "claims";
    }


}

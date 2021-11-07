package bg.obag.obag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/favourites")
public class FavouritesController {

    @GetMapping()
    public String getOrdersPage() {
        return "favourites";
    }
}

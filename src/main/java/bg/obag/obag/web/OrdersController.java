package bg.obag.obag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    /* ------------------------------------ EXCEPTION HANDLER ------------------------------------------------- */





    /* ------------------------------------------ ORDERS ------------------------------------------------------ */

    @GetMapping()
    public String getOrdersPage() {
        return "orders";
    }
}

package bg.obag.obag.service;

import bg.obag.obag.model.binding.UserOrderBindModel;
import bg.obag.obag.model.service.OrderServiceModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OrderService {
    OrderServiceModel createOrder(Long cartId, String payment, String delivery, UserOrderBindModel userOrderBindModel);

    List<OrderServiceModel> findAllOrdersForUser(UserDetails principal);
}

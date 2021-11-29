package bg.obag.obag.service.impl;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.binding.UserOrderBindModel;
import bg.obag.obag.model.entity.OrderEntity;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.Status;
import bg.obag.obag.model.service.CartServiceModel;
import bg.obag.obag.model.service.OrderServiceModel;
import bg.obag.obag.model.view.ProductCategoryViewModel;
import bg.obag.obag.repo.OrderRepo;
import bg.obag.obag.service.CartService;
import bg.obag.obag.service.OrderService;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final ProductsService productsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepo orderRepo, CartService cartService, ProductsService productsService, UserService userService, ModelMapper modelMapper) {
        this.orderRepo = orderRepo;
        this.cartService = cartService;
        this.productsService = productsService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderServiceModel createOrder(Long cartId, String payment, String delivery,
                                         UserOrderBindModel userOrderBindModel) {
        CartServiceModel cartServiceModel = cartService.findById(cartId);

        List<ProductEntity> productEntities = cartServiceModel.getProducts().stream()
                .map(product -> {
                    try {
                        return productsService.findProductEntityById(product.getId());
                    } catch (ProductNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());


        OrderEntity orderEntity = new OrderEntity()
                .setProducts(productEntities)
                .setTotalValue(cartServiceModel.getTotalValue())
//                .setPaymentMethod(Payment.valueOf(payment.toUpperCase()))
                .setStatus(Status.НОВА)
                .setTrackingNumber("0123456789") // TODO: fix after setup Speedy API
                .setNote("This is a helper test note to an order...");

        Optional<UserEntity> userEntityOpt = userService.findByEmail(userOrderBindModel.getEmail());
        userEntityOpt.ifPresent(orderEntity::setUser);

        OrderEntity savedOrderEntity = orderRepo.save(orderEntity);

        return modelMapper.map(savedOrderEntity, OrderServiceModel.class)
                .setProducts(savedOrderEntity.getProducts().stream()
                        .map(productEntity -> modelMapper.map(productEntity, ProductCategoryViewModel.class))
                        .collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public List<OrderServiceModel> findAllOrdersForUser(UserDetails principal) {
        UserEntity userEntity = userService.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + principal.getUsername() + " not found."));

        List<OrderEntity> orderEntities = orderRepo.findAllByUserOrderByCreatedOnDesc(userEntity);

        return orderEntities.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, OrderServiceModel.class)
                        .setProducts(orderEntity.getProducts().stream()
                                .map(productEntity -> modelMapper.map(productEntity, ProductCategoryViewModel.class))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}

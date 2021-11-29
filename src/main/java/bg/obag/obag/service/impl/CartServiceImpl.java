package bg.obag.obag.service.impl;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.entity.CartEntity;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.CartServiceModel;
import bg.obag.obag.model.view.ProductCategoryViewModel;
import bg.obag.obag.model.view.UserViewModel;
import bg.obag.obag.repo.CartRepo;
import bg.obag.obag.service.CartService;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.UserService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private static final BigDecimal FREE_DELIVERY_FROM = BigDecimal.valueOf(50.0);
    private final CartRepo cartRepo;
    private final ProductsService productsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepo cartRepo, ProductsService productsService, UserService userService, ModelMapper modelMapper) {
        this.cartRepo = cartRepo;
        this.productsService = productsService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartServiceModel createCart(Long productId, UserDetails principal) throws ProductNotFoundException {
        List<ProductEntity> products = new ArrayList<>();
        products.add(productsService.findProductEntityById(productId));

        BigDecimal cartValue = getCartValue(products);

        BigDecimal deliveryCost = getDeliveryCost(cartValue);

        UserEntity userEntity = getUserEntity(principal);

        CartEntity cartEntity = new CartEntity()
                .setProducts(products)
                .setDeliveryCost(deliveryCost)
                .setTotalValue(cartValue)
                .setUser(userEntity);
        CartEntity savedCartEntity = cartRepo.save(cartEntity);

        return modelMapper.map(savedCartEntity, CartServiceModel.class).setProducts(
                savedCartEntity.getProducts().stream()
                        .map(productEntity -> modelMapper.map(productEntity, ProductCategoryViewModel.class))
                        .collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public CartServiceModel addProductToCart(Long cartId, Long productId, UserDetails principal) throws ProductNotFoundException {
        ProductEntity productEntity = productsService.findProductEntityById(productId);

        CartEntity cartEntity = cartRepo.findById(cartId).get();
        cartEntity.getProducts().add(productEntity);

        BigDecimal cartValue;
        if (cartEntity.getProducts().size() > 0)
            cartValue = getCartValue(cartEntity.getProducts());
        else
            cartValue = BigDecimal.ZERO;

        cartEntity.setTotalValue(cartValue);

        cartEntity.setDeliveryCost(getDeliveryCost(cartValue));

        if (principal != null)
            cartEntity.setUser(userService.findByEmail(principal.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + principal.getUsername() + " not exist.")));

        CartEntity savedCartEntity = cartRepo.save(cartEntity);

        return modelMapper.map(savedCartEntity, CartServiceModel.class).setProducts(
                savedCartEntity.getProducts().stream()
                        .map(pe -> modelMapper.map(productEntity, ProductCategoryViewModel.class))
                        .collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public CartServiceModel deleteProductFromCart(Long cartId, Long productId) throws ProductNotFoundException {
        ProductEntity productEntity = productsService.findProductEntityById(productId);

        CartEntity cartEntity = cartRepo.findById(cartId).get();
        cartEntity.getProducts().remove(productEntity);

        BigDecimal cartValue;
        if (cartEntity.getProducts().size() > 0)
            cartValue = getCartValue(cartEntity.getProducts());
        else
            cartValue = BigDecimal.ZERO;

        cartEntity.setTotalValue(cartValue);

        cartEntity.setDeliveryCost(getDeliveryCost(cartValue));

        CartEntity savedCartEntity = cartRepo.save(cartEntity);

        return modelMapper.map(savedCartEntity, CartServiceModel.class).setProducts(
                savedCartEntity.getProducts().stream()
                        .map(pe -> modelMapper.map(productEntity, ProductCategoryViewModel.class))
                        .collect(Collectors.toList()));
    }

    @Override
    public void deleteSubmittedCart(Long cartId) throws ObjectNotFoundException {
        CartEntity cartEntity = cartRepo.findById(cartId)
                .orElseThrow(() -> new ObjectNotFoundException("Cart with id " + cartId + " dont exists."));
        cartEntity.setUser(null);
        cartRepo.deleteById(cartId);
    }

    @Scheduled(cron = "0 0 1 * * *") // every 1st.date of month
    @Override
    public void deleteSubmittedCart() {
        LocalDateTime localDateTime = LocalDateTime.now().minusMonths(6);
        List<CartEntity> allOlderThanSixMonths = cartRepo.findAllOlderThanSixMonths(localDateTime);
        allOlderThanSixMonths.stream()
                        .map(cartEntity -> cartEntity.setUser(null))
                                .collect(Collectors.toList());

        cartRepo.deleteOlderThanSixMonths(localDateTime);
    }

    private UserEntity getUserEntity(UserDetails principal) {
        UserEntity userEntity = null;
        if (principal != null) {
            userEntity = userService.findByEmail(principal.getUsername()).get();
        }
        return userEntity;
    }

    private BigDecimal getDeliveryCost(BigDecimal cartValue) {
        BigDecimal deliveryCost = BigDecimal.ZERO;
        if (cartValue.compareTo(FREE_DELIVERY_FROM) < 0) {
            deliveryCost = BigDecimal.valueOf(4.00);
        }
        return deliveryCost;
    }

    private BigDecimal getCartValue(List<ProductEntity> products) {
        return products.stream()
                .map(ProductEntity::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }


    @Transactional
    @Override
    public CartServiceModel findById(Long id) {
        CartEntity cartEntity = cartRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart with id " + id + " dont exists."));

        CartServiceModel cartServiceModel = modelMapper.map(cartEntity, CartServiceModel.class)
                .setProducts(cartEntity.getProducts().stream()
                        .map(productEntity -> modelMapper.map(productEntity, ProductCategoryViewModel.class))
                        .collect(Collectors.toList()));

        if (cartEntity.getUser() != null) {
            cartServiceModel.setUser(modelMapper.map(cartEntity.getUser(), UserViewModel.class));
        }

        return cartServiceModel;
    }
}

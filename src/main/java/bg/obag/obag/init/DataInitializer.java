package bg.obag.obag.init;

import bg.obag.obag.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final SeasonService seasonService;
    private final DeliveryService deliveryService;

    public DataInitializer(RoleService roleService, UserService userService, CategoryService categoryService, SeasonService seasonService, DeliveryService deliveryService) {
        this.roleService = roleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.seasonService = seasonService;
        this.deliveryService = deliveryService;
    }

    @Transactional
    @Override
    public void run(String... args) {
        roleService.initializeRoles();
        userService.initializeAdmin();
//        categoryService.initializeCategories();
//        seasonService.initializeSeasons();
        deliveryService.initializeDeliveries();
    }
}

package bg.obag.obag.init;

import bg.obag.obag.service.CategoryService;
import bg.obag.obag.service.DeliveryService;
import bg.obag.obag.service.RoleService;
import bg.obag.obag.service.SeasonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final CategoryService categoryService;
    private final SeasonService seasonService;
    private final DeliveryService deliveryService;

    public DataInitializer(RoleService roleService, CategoryService categoryService, SeasonService seasonService, DeliveryService deliveryService) {
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.seasonService = seasonService;
        this.deliveryService = deliveryService;
    }

    @Transactional
    @Override
    public void run(String... args) {
        roleService.initializeRoles();
        categoryService.initializeCategories();
        seasonService.initializeSeasons();
        deliveryService.initializeDeliveries();
    }
}

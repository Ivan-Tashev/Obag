package bg.obag.obag.init;

import bg.obag.obag.service.CategoryService;
import bg.obag.obag.service.RoleService;
import bg.obag.obag.service.SeasonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final CategoryService categoryService;
    private final SeasonService seasonService;

    public DataInitializer(RoleService roleService, CategoryService categoryService, SeasonService seasonService) {
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.seasonService = seasonService;
    }

    @Override
    public void run(String... args) {
        roleService.initializeRoles();
        categoryService.initializeCategories();
        seasonService.initializeSeasons();
    }
}

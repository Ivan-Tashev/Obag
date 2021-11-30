package bg.obag.obag.web;

import bg.obag.obag.model.binding.CategoryBindModel;
import bg.obag.obag.model.entity.CategoryEntity;
import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.CategoryRepo;
import bg.obag.obag.repo.RoleRepo;
import bg.obag.obag.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "a@b.c", roles = {"ADMIN"})
class CategoryControllerTest {
    private static final String ROUTE_PREFIX = "/category";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        UserEntity userEntity = initUser();
        userRepo.save(userEntity);

        CategoryBindModel categoryBindModel = initCategory();
        CategoryEntity categoryEntity = objectMapper.convertValue(categoryBindModel, CategoryEntity.class)
                .setCreatedBy(userEntity);
        categoryRepo.save(categoryEntity);
    }

    @AfterEach
    public void tearDown() {
        categoryRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void getCategoryPage() throws Exception {
        CategoryBindModel categoryBindModel = initCategory();

        mockMvc.perform(get(ROUTE_PREFIX + "/" + categoryBindModel.getCategory()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getCategoryPage"))
                .andExpect(model().attributeExists("productsInCategory"))
                .andExpect(model().attributeExists("categoryImage"))
                .andExpect(model().attribute("categoryImage",
                        categoryRepo.findByCategory(categoryBindModel.getCategory()).get().getImage()))
                .andExpect(view().name("category"));
    }

    @Test
    public void getAddCategoryPage() throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX + "/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCategories"))
                .andExpect(view().name("addCategory"));
    }

    @Test
    public void addCategory_InvalidInput() throws Exception {
        CategoryBindModel categoryBindModel = initCategory()
                .setCategory("A");

        mockMvc.perform(post(ROUTE_PREFIX + "/add")
                        .content(objectMapper.writeValueAsString(categoryBindModel))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("addCategory"))
                .andExpect(flash().attributeExists("categoryBindModel"))
                .andExpect(redirectedUrl("/category/add"));
    }

    @Test
    public void addCategory_ExistingNameInDatabase() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("category", "TestCategory")
                        .sessionAttr("categoryBindModel", new CategoryBindModel())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("categoryExist"))
                .andExpect(redirectedUrl("/category/add"));
    }

    @Test
    public void addCategory_AddNewCategory() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/add")
                        .param("category", "NewCategory")
                        .sessionAttr("categoryBindModel", new CategoryBindModel())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successfullyAddedCategory", true))
                .andExpect(redirectedUrl("/category/add"));

        Assertions.assertEquals(2, categoryRepo.count());
    }

    @Test
    public void getEditCategoryPage() throws Exception {
        CategoryEntity testCategory = categoryRepo.findByCategory("TestCategory").get();

        mockMvc.perform(get(ROUTE_PREFIX + "/edit/" + testCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryBindModel"))
                .andExpect(model().attributeExists("allCategories"))
                .andExpect(view().name("addCategory"));
    }


    private CategoryBindModel initCategory() {
        return new CategoryBindModel()
                .setCategory("TestCategory")
                .setCreatedOn(LocalDateTime.now())
                .setPriority(1)
                .setImage("http://a");
    }

    public UserEntity initUser() {
        RoleEntity roleAdmin = new RoleEntity().setRole(RoleEnum.ADMIN);
//        List<RoleEntity> roleEntities = roleRepo.saveAll(List.of(roleUser, roleAdmin, roleSuperadmin));
        List<RoleEntity> roleEntities = roleRepo.findAll();

        UserEntity userEntity = new UserEntity()
                .setEmail("a@b.c")
                .setFirstName("John")
                .setLastName("Smith")
                .setPassword("123")
                .setPhone("123456")
                .setRoleEntities(new ArrayList<>(roleEntities));
        userEntity.setCreatedOn();
        userEntity.setProducts(new ArrayList<>());
        return userEntity;
    }
}
package bg.obag.obag.web;

import bg.obag.obag.model.entity.*;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "a@b.c", roles = {"ADMIN"})
class LogControllerTest {
    private static final String ROUTE_PREFIX = "/logs";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    LogRepo logRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private VisitRepo visitRepo;

    @BeforeEach
    public void setup() {
        UserEntity userEntity = userRepo.save(initUser());
        ProductEntity productEntity = productRepo.save(initProduct(userEntity));
        logRepo.save(createLogEntity(userEntity, productEntity));
        visitRepo.save(new VisitEntity().setDate(LocalDate.now()).setTotalVisits(5).setUniqueVisits(1));
    }

    @AfterEach
    public void tearDown() {
        logRepo.deleteAll();
        productRepo.deleteAll();
        userRepo.deleteAll();
        visitRepo.deleteAll();
    }

    @Test
    public void getLogsPage() throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("logs"));
    }

//    @Test
//    public void getAllLogs() throws Exception {
//        mockMvc.perform(get(ROUTE_PREFIX + "/products"))
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", hasSize(1)));
//                .andExpect(jsonPath("$.[0].id", is(1L)))
//                .andExpect(jsonPath("$.[1].name", is(1L)))
//    }

    @Test
    public void getAllLogsByUser() throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX + "/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].productName").value(is("Product1")))
                .andExpect(jsonPath("$.[0].user").value(is("a@b.c")));
    }

    @Test
    public void getVisits() throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX + "/views"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].uniqueVisits").value(is(1)))
                .andExpect(jsonPath("$.[0].totalVisits").value(is(5)));
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

    private LogEntity createLogEntity(UserEntity user, ProductEntity product) {
        return new LogEntity().setAction("Test")
                .setUser(user)
                .setProduct(product);
    }

    private ProductEntity initProduct(UserEntity user) {
        return new ProductEntity()
                .setName("Product1")
                .setSku("123")
                .setCost(BigDecimal.ZERO)
                .setPrice(BigDecimal.ZERO)
                .setDescription(".................")
                .setImage("http://image.jpg")
                .setCreatedBy(user);
    }
}
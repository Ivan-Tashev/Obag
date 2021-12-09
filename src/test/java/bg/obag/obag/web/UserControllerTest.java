package bg.obag.obag.web;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.RoleRepo;
import bg.obag.obag.repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String ROUTE_PREFIX = "/users";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    public void init() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleEnum.USER);
        roleRepo.save(roleEntity);

        UserEntity userEntity = new UserEntity()
                .setEmail("a@b.c")
                .setFirstName("John")
                .setLastName("Smith")
                .setPassword("123")
                .setPhone("123456")
                .setRoleEntities(List.of(roleEntity))
                .setRegisteredOn(LocalDateTime.now())
                .setNote("Some note for user...")
                .setNewsletter(false)
                .setCity("NY")
                .setCountry("USA")
                .setAddress("Some street 5")
                .setPostCode("NYC123")
                .setCountOrders(0)
                .setValueOrders(BigDecimal.ZERO)
                .setPrivacyPolicy(true);
        userEntity.setCreatedOn();
        userEntity.setProducts(new ArrayList<>());

        userRepo.save(userEntity);
    }

    @AfterEach
    public void tearDown() {
        userRepo.deleteAll();
        roleRepo.deleteAll();
    }

    @Test
    public void getLogin_ReturnLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROUTE_PREFIX + "/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void loginError_RedirectBackToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_PREFIX + "/login-error")
                        .param("email", "x@y.z")
                        .param("password", "111")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bad_credentials", "userLoginBindingModel"))
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    public void getRegister_ReturnAttributeAndView () throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX + "/register"))
                .andExpect(model().attributeExists("userRegisterBindingModel"))
                .andExpect(view().name("register"));
    }

    @Test
    public void register_NotMatchingPasswords_ReturnViewRegister() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/register")
                        .param("email", "x@y.z")
                        .param("password", "123")
                        .param("rePassword", "456")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(flash().attribute("notMatch", true));
    }

    @Test
    public void register_EmailExists_ReturnViewRegister() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/register")
                        .param("email", "a@b.c")
                        .param("password", "123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(flash().attributeExists("found"));
    }

    @Test
    public void register_InvalidInput_ReturnViewRegister() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/register")
                        .param("firstName", "J")
                        .param("lastName", "S")
                        .param("email", "abc.com")
                        .param("phone", "a1")
                        .param("password", "123")
                        .param("rePassword", "123")
                        .param("privacyPolicy", "true")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(flash().attributeExists("userRegisterBindingModel"));
    }

    @Test
    public void register_RegisterNewUser() throws Exception {
        userRepo.deleteAll();
        Assertions.assertEquals(0, userRepo.count());

        mockMvc.perform(post(ROUTE_PREFIX + "/register")
                .param("firstName", "Anne")
                .param("lastName", "Nicol")
                .param("email", "anl2@abv.bg")
                .param("phone", "123456")
                .param("password", "123")
                .param("rePassword", "123")
                .param("privacyPolicy", "true")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        Assertions.assertEquals(1, userRepo.count());
    }

}
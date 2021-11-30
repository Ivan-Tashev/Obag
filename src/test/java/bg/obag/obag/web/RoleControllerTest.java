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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {
    private static final String ROUTE_PREFIX = "/roles";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    public void init() {
        UserEntity userEntity = initUser();

        userRepo.save(userEntity);
    }

    public UserEntity initUser() {
        RoleEntity roleUser = new RoleEntity().setRole(RoleEnum.USER);
        RoleEntity roleAdmin = new RoleEntity().setRole(RoleEnum.ADMIN);
        RoleEntity roleSuperadmin = new RoleEntity().setRole(RoleEnum.SUPERADMIN);
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

    @AfterEach
    public void tearDown() {
        userRepo.deleteAll();
        roleRepo.deleteAll();
    }

    @Test
    @WithMockUser(username = "a@b.c", roles = {"SUPERADMIN"})
    public void getRoles_ReturnAllRoles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROUTE_PREFIX + "/change"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roles"))
                .andExpect(view().name("changeRole"));

        Assertions.assertEquals(0, roleRepo.count());
    }

    @Test
    @WithMockUser(username = "a@b.c", roles = {"SUPERADMIN"})
    public void getRolesForEmail_ShowRoles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ROUTE_PREFIX + "/change/show")
                        .param("email", "a@b.c"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userRoles"))
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attributeExists("email"))
                .andExpect(view().name("changeRole"));
    }

    @Test
    @WithMockUser(username = "a@b.c", roles = {"SUPERADMIN"})
    public void changeRole_ReturnSuccessAndRedirect() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/change")
                        .param("email", "a@b.c")
                        .param("role", "USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("successfullyChangedRole"))
                .andExpect(redirectedUrl("/roles/change"));
    }

    @Test
    @WithMockUser(username = "a@b.c", roles = {"SUPERADMIN"})
    public void removeRole_ReturnSuccessAndRedirect() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/remove")
                        .param("email", "a@b.c")
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("successfullyChangedRole"))
                .andExpect(redirectedUrl("/roles/change"));
    }
}
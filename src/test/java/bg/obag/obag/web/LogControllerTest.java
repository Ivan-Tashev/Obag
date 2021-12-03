package bg.obag.obag.web;

import bg.obag.obag.model.custom.ProductsLogCount;
import bg.obag.obag.model.entity.LogEntity;
import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.LogRepo;
import bg.obag.obag.repo.RoleRepo;
import bg.obag.obag.repo.SeasonRepo;
import bg.obag.obag.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    public void setup() {

    }


    @Test
    public void getLogsPage() throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("logs"));
    }

    @Test
    public void getAllLogs() throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
//                .andExpect(jsonPath("$.[0].id", is(1L)))
//                .andExpect(jsonPath("$.[1].name", is(1L)))
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
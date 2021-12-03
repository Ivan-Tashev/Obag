package bg.obag.obag.web;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.RoleRepo;
import bg.obag.obag.repo.UserRepo;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;


    @Test
    @WithMockUser(username = "a@b.c")
    public void getHome() throws Exception {
        UserEntity savedUserEntity = userRepo.save(initUser());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    public UserEntity initUser() {
        RoleEntity roleAdmin = new RoleEntity().setRole(RoleEnum.USER);
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
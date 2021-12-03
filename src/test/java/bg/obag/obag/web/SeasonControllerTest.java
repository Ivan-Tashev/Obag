package bg.obag.obag.web;

import bg.obag.obag.exception.SeasonNotFoundException;
import bg.obag.obag.model.binding.SeasonBindModel;
import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.SeasonEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.RoleRepo;
import bg.obag.obag.repo.SeasonRepo;
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
class SeasonControllerTest {
    private static final String ROUTE_PREFIX = "/season";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SeasonRepo seasonRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        userRepo.deleteAll();
        UserEntity userEntity = initUser();
        userRepo.save(userEntity);

        SeasonBindModel seasonBindModel = initSeason();
        SeasonEntity seasonEntity = objectMapper.convertValue(seasonBindModel, SeasonEntity.class)
                .setCreatedBy(userEntity);
        seasonRepo.save(seasonEntity);
    }

    @AfterEach
    public void tearDown() {
        seasonRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void getAddSeasonPage() throws Exception {
        mockMvc.perform(get(ROUTE_PREFIX + "/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("seasonBindModel"))
                .andExpect(model().attributeExists("allSeasons"))
                .andExpect(view().name("addSeason"));
    }

    @Test
    public void addSeason_InvalidInputData() throws Exception {
        SeasonBindModel seasonBindModel = initSeason().setSeason("A");

        mockMvc.perform(post(ROUTE_PREFIX + "/add")
                        .content(objectMapper.writeValueAsString(seasonBindModel))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("seasonBindModel"))
                .andExpect(redirectedUrl("/season/add"));
    }

    @Test
    public void addSeason_ExistingNameInDatabase() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("season", "TestSeason")
                        .sessionAttr("seasonBindModel", new SeasonBindModel())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("seasonExist"))
                .andExpect(redirectedUrl("/season/add"));
    }

    @Test
    public void addSeason_AddedNewSeason() throws Exception {
        mockMvc.perform(post(ROUTE_PREFIX + "/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("season", "NewSeason")
                        .sessionAttr("seasonBindModel", new SeasonBindModel())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("successfullyAddedSeason"))
                .andExpect(redirectedUrl("/season/add"));

        Assertions.assertEquals(2, seasonRepo.count());
    }

    @Test
    public void getEditSeasonPage() throws Exception {
        SeasonEntity testSeason = seasonRepo.findBySeason("TestSeason").get();

        mockMvc.perform(get(ROUTE_PREFIX + "/edit/" + testSeason.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("seasonBindModel"))
                .andExpect(model().attributeExists("allSeasons"))
                .andExpect(view().name("addSeason"));
    }

    @Test
    public void seasonServiceGetBySeason_ThrowsException() throws Exception {
        long notExistingSeasonId = 99L;

        mockMvc.perform(get(ROUTE_PREFIX + "/edit/" + notExistingSeasonId))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof SeasonNotFoundException));
    }


    private SeasonBindModel initSeason() {
        return new SeasonBindModel()
                .setSeason("TestSeason")
                .setCreatedOn(LocalDateTime.now())
                .setPriority(1);
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
package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.UserRepo;
import bg.obag.obag.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserEntity testUserEntity;
    private UserService serviceToTest;
    @Mock
    private UserRepo mockedUserRepo;

    @BeforeEach
    public void setup() {
//        this.serviceToTest = new UserServiceImpl(mockedUserRepo);

        RoleEntity userRole = new RoleEntity();
        userRole.setRole(RoleEnum.USER);
        RoleEntity adminRole = new RoleEntity();
        adminRole.setRole(RoleEnum.ADMIN);

        this.testUserEntity = new UserEntity()
                .setFirstName("A").setLastName("B")
                .setEmail("a@b.c")
                .setPassword("123")
                .setRoleEntities(List.of(userRole, adminRole));
    }

}
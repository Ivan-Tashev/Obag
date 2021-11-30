package bg.obag.obag.security;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class ObagUserDetailsServiceTest {
    private UserEntity testUserEntity;
    private UserDetailsService serviceToTest;
    @Mock
    private UserRepo mockedUserRepo;

    @BeforeEach
    public void init() {
        this.serviceToTest = new ObagUserDetailsService(mockedUserRepo);

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

    @Test
    void loadUserByUsername_ThrowUserNotFoundException() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("Not found..."));
    }

    @Test
    public void loadUserByUsername_ReturnUser() {
        Mockito.when(mockedUserRepo.findByEmail("a@b.c"))
                .thenReturn(Optional.of(this.testUserEntity));

        UserDetails userDetails = serviceToTest.loadUserByUsername("a@b.c");

        Assertions.assertEquals(testUserEntity.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        List<String> authoritiesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Assertions.assertTrue(authoritiesList.contains("ROLE_USER"));
    }

}
package bg.obag.obag.security;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.repo.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.lang.model.element.UnknownDirectiveException;
import java.util.Optional;

class ObagUserDetailsServiceTest {
    private UserEntity testUserEntity;
    private UserDetailsService obagUserDetailsService;
    @Mock
    private UserRepo mockedUserRepo;

    @BeforeEach
    public void init() {
        this.testUserEntity = new UserEntity()
                .setFirstName("A").setLastName("B").setEmail("a@b.c").setPassword("123");

        this.obagUserDetailsService = new ObagUserDetailsService(mockedUserRepo);

    }

//    @Test
//    void loadUserByUsername_ThrowUserNotFoundException() {
//
//        Mockito.when(mockedUserRepo.findByEmail(this.testUserEntity.getEmail()))
//                        .thenReturn(obagUserDetailsService.loadUserByUsername(""));
//
//        Assertions.assertThrows(UsernameNotFoundException.class,
//                ob
//                )
//    }

}
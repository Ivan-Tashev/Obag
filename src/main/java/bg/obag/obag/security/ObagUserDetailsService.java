package bg.obag.obag.security;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.repo.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObagUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public ObagUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " dont exists."));

        Set<GrantedAuthority> authorities = userEntity.getRoleEntities().stream()
                .map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getRole().name()))
                .collect(Collectors.toSet());

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}

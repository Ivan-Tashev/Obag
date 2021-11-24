package bg.obag.obag.config;

import bg.obag.obag.security.ObagUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableWebSecurity // optional
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObagUserDetailsService obagUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(ObagUserDetailsService obagUserDetailsService, PasswordEncoder passwordEncoder) {
        this.obagUserDetailsService = obagUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(obagUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/roles/change").hasRole("SUPERADMIN")
                .antMatchers("/products/add", "/products/import", "/products/delete/**",
                        "/logs/**").hasRole("ADMIN")
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/actuator/**").hasRole("SUPERADMIN")
                .anyRequest().permitAll()
                .and()
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .formLogin().loginPage("/users/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/cart")
                .failureForwardUrl("/users/login-error")
                .and()
                .logout().logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}

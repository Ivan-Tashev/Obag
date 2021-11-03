package bg.obag.obag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableCaching
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ObagApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObagApplication.class, args);
    }

}

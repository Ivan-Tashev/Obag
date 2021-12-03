package bg.obag.obag.config;

import bg.obag.obag.web.intercept.VisitsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final VisitsInterceptor visitsInterceptor;

    public WebConfig(VisitsInterceptor visitsInterceptor) {
        this.visitsInterceptor = visitsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitsInterceptor);
    }
}

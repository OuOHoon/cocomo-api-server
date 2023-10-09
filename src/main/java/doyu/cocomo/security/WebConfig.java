package doyu.cocomo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:5173", "https://ouohoon.github.io")
                .allowedMethods("GET", "POST", "DELETE")
                .allowedHeaders("X-XSRF-TOKEN", "Content-Type")
                .allowCredentials(true); // 인증과 관련된 쿠키 전송 허용
    }
}

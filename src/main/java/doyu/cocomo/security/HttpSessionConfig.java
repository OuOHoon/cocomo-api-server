package doyu.cocomo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;


// Spring session 환경설정 클래스
@Configuration
@EnableJdbcHttpSession
public class HttpSessionConfig {
}

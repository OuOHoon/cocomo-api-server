package doyu.cocomo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import doyu.cocomo.member.dto.MemberLoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    private AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        super("/auth/login");
        this.authenticationManager = authenticationManager; // AbstractAuthenticationProcessingFilter는 반드시 manager 소유
        setAuthenticationManager(authenticationManager);
        this.objectMapper = new ObjectMapper();
        log.info("loginFilter: authenticationManager={}", this.getAuthenticationManager().toString());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("LoginFilter, attemptAutehntication method 실행");

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method");
        }

        MemberLoginRequest memberLoginRequest;
        try (InputStream is = request.getInputStream()) {
            memberLoginRequest = objectMapper.readValue(is, MemberLoginRequest.class);
            log.info("memberLoginRequest 정보: {}", memberLoginRequest.getCocomoKey());
        }

        KeyAuthenticationToken keyAuthenticationToken = new KeyAuthenticationToken(memberLoginRequest.getCocomoKey());

        return this.getAuthenticationManager().authenticate(keyAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("key", authResult.getCredentials());
        SecurityContextHolder.getContext().setAuthentication(authResult);
        log.info("successfulAuthentication!!");
    }
}

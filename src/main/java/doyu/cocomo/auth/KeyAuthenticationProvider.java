package doyu.cocomo.auth;

import doyu.cocomo.member.MemberRepository;
import doyu.cocomo.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class KeyAuthenticationProvider implements AuthenticationProvider {

    private MemberRepository memberRepository;

    public KeyAuthenticationProvider(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String cocomoKey = authentication.getCredentials().toString();
        log.info("authenticationProvider: credentials={}", cocomoKey);

        Optional<Member> optionalMember = memberRepository.findByCocomoApiKey(cocomoKey);
        if (optionalMember.isEmpty()) {
            throw new BadCredentialsException("Invalid cocomo key");
        }
        KeyAuthenticationToken token = new KeyAuthenticationToken(cocomoKey);
        token.setDetails(authentication.getDetails());
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(KeyAuthenticationToken.class);
    }

}


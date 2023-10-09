package doyu.cocomo.security;

import doyu.cocomo.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/csrf")
    public ResponseEntity<String> getCsrfToken() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 유효한 세션인지 체크
    @PostMapping("/session-check")
    public ResponseEntity<String> sessionCheck() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        request.getSession().invalidate();

        // 쿠키 삭제
        Cookie sessionCookie = new Cookie("SESSION", null);
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);

        return ResponseEntity.ok("Logged out");
    }
}

package doyu.cocomo.member;

import doyu.cocomo.common.response.BaseResponse;
import doyu.cocomo.member.dto.MemberCreateRequest;
import doyu.cocomo.member.dto.MemberCreateResponse;
import doyu.cocomo.member.dto.MemberLoginRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    // 테스트
    @GetMapping
    public ResponseEntity<BaseResponse<String>> getMember() {
        return ResponseEntity.ok()
                .body(BaseResponse.ok("getMember ok"));
    }

    // 회원 정보 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<BaseResponse<String>> getMemberById(@PathVariable String memberId) {
        log.info("memberid get method");
        return ResponseEntity.ok()
                .header("test-header", "headerValue")
                .body(BaseResponse.ok("testdata"));
    }

    // 회원가입
    @PostMapping("/join")
    public BaseResponse<MemberCreateResponse> join(@RequestBody MemberCreateRequest memberCreateRequest) {
        MemberCreateResponse response = memberService.createMember(memberCreateRequest);
        return BaseResponse.ok(response);
    }
}

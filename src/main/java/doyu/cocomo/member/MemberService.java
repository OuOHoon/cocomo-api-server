package doyu.cocomo.member;

import doyu.cocomo.member.dto.MemberCreateRequest;
import doyu.cocomo.member.dto.MemberCreateResponse;
import doyu.cocomo.member.dto.MemberLoginRequest;
import doyu.cocomo.member.dto.MemberLoginResponse;
import doyu.cocomo.member.entity.Member;

public interface MemberService {

    // 회원 가입
    public MemberCreateResponse createMember(MemberCreateRequest memberCreateRequest);

    // 코코모 키 생성
    public String createCocomoKey();

    public String getApiKeyByCocomoKey(String cocomoKey);

    // 중복 가입 확인
    public boolean isExistMember(Long discordId, String apiKey);

    // 로아 API 키 유효한지 확인
    public boolean isValidLostarkApiKey(String apiKey);

    // 코코모 키 유효한지 확인
    public Boolean isValidCocomoApiKey(String key);

    // 같은 유저의 API 키인지 확인
    public Boolean isValidApiKeyPair(String cocomoApiKey, String lostarkApiKey);
}

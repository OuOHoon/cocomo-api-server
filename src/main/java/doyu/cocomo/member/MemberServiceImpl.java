package doyu.cocomo.member;

import doyu.cocomo.member.dto.MemberCreateRequest;
import doyu.cocomo.member.dto.MemberCreateResponse;
import doyu.cocomo.member.dto.MemberLoginRequest;
import doyu.cocomo.member.dto.MemberLoginResponse;
import doyu.cocomo.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private StringEncryptor stringEncryptor;
    private final WebClient webClient = WebClient.builder()
            .build();
    private final String apiTestUrl = "https://developer-lostark.game.onstove.com/characters/아잉눈1/siblings";

    @Override
    public MemberCreateResponse createMember(MemberCreateRequest memberCreateRequest) {

        if (isExistMember(memberCreateRequest.getDiscordId(), memberCreateRequest.getApiKey())) {
            return null;
        }
        if (!isValidLostarkApiKey(memberCreateRequest.getApiKey())) {
            return null;
        }

        Member member = Member.toEntity(memberCreateRequest);
        member.setLostarkApiKey(stringEncryptor.encrypt(member.getLostarkApiKey()));
        member.initCocomoApiKey(createCocomoKey());
        Member save = memberRepository.save(member);
        return save.toResponse();
    }


    @Override
    public String createCocomoKey() {
        UUID uuid = UUID.randomUUID(); // 확률 낮아서 중복 검사 안해도 된다고 생각
        return uuid.toString();
    }

    @Override
    public boolean isExistMember(Long discordId, String apiKey) {
        Optional<Member> optionalMember = memberRepository.findByLostarkApiKeyOrDiscordId(apiKey, discordId);
        if (optionalMember.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public String getApiKeyByCocomoKey(String cocomoKey) {
        Optional<Member> optionalMember = memberRepository.findByCocomoApiKey(cocomoKey);
        if (optionalMember.isEmpty()) {
            return null;
        }
        String lostarkApiKey = optionalMember.get().getLostarkApiKey();
        String decryptKey = stringEncryptor.decrypt(lostarkApiKey);
        log.info("decrypt key={}", decryptKey);
        return decryptKey;
    }

    @Override
    public boolean isValidLostarkApiKey(String apiKey) {
        try {
            ResponseEntity<Void> responseEntity = webClient.get()
                    .uri(apiTestUrl)
                    .header("authorization", "bearer " + apiKey)
                    .header("accept", "application/json")
                    .retrieve().toBodilessEntity()
                    .block();
            log.info("유효한 API 키인지 체크 메소드의 상태 코드: " + responseEntity.getStatusCode().toString());

            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (WebClientResponseException e) {
            return false;
        }
    }

    @Override
    public Boolean isValidCocomoApiKey(String key) {
        Optional<Member> optionalMember = memberRepository.findByCocomoApiKey(key);

        if (optionalMember.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean isValidApiKeyPair(String cocomoApiKey, String lostarkApiKey) {
        Optional<Member> optionalMember = memberRepository.findByCocomoApiKey(cocomoApiKey);
        if (optionalMember.isEmpty()) {
            return false;
        } // todo: 코코모 인증키랑 로스트아크 인증키랑 같은 유저의 키인지 확인
        if (optionalMember.get().getLostarkApiKey().equals(lostarkApiKey)) {
            return false;
        }
        return true;
    }
}

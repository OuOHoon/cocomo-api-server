package doyu.cocomo.member;

import doyu.cocomo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByCocomoApiKey(String key);
    Optional<Member> findByLostarkApiKey(String key);
    Optional<Member> findByDiscordId(Long discordId);
    Optional<Member> findByLostarkApiKeyOrDiscordId(String key, Long discordId);
}

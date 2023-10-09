package doyu.cocomo.member.entity;

import doyu.cocomo.alarm.entity.Alarm;
import doyu.cocomo.member.dto.MemberCreateRequest;
import doyu.cocomo.member.dto.MemberCreateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {


    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private Long discordId;
    @Column(length = 1000)
    private String lostarkApiKey;
    @Column(unique = true)
    private String cocomoApiKey; // 웹 서비스 로그인에 사용할 키

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Alarm> alarms;

    public void addAlarm(Alarm alarm) {
        this.alarms.add(alarm);
        alarm.setMember(this);
    }

    public void setLostarkApiKey(String lostarkApiKey) {
        this.lostarkApiKey = lostarkApiKey;
    }

    public void initCocomoApiKey(String cocomoApiKey) {
        this.cocomoApiKey = cocomoApiKey;
    }

    public static Member toEntity(MemberCreateRequest memberCreateRequest) {
        return Member.builder()
                .discordId(memberCreateRequest.getDiscordId())
                .lostarkApiKey(memberCreateRequest.getApiKey())
                .build();
    }

    public MemberCreateResponse toResponse() {
        return MemberCreateResponse.builder()
                .cocomoKey(this.cocomoApiKey)
                .build();
    }
}

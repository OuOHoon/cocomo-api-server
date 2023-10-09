package doyu.cocomo.alarm.entity;

import doyu.cocomo.alarm.dto.AlarmCreateRequest;
import doyu.cocomo.alarm.dto.AlarmDataResponse;
import doyu.cocomo.auction.model.RequestAuctionItems;
import doyu.cocomo.auction.model.SearchDetailOption;
import doyu.cocomo.member.entity.Member;
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
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int categoryCode = 200000;
    private Integer statCode1 = null;
    private Integer statCode2 = null;

    private Integer abilityCode1 = null;
    private Integer abilityMinValue1 = null;
    private Integer abilityCode2 = null;
    private Integer abilityMinValue2 = null;
    private Integer penaltyCode = null;
    private Integer penaltyMaxValue = null;
    private Integer quality = null;
    private Integer tradeCount = 0;
    private Integer price = 10000;

    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL)
    private List<AlarmLog> alarmLogs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public void addAlarmLog(AlarmLog alarmLog) {
        alarmLog.setAlarm(this);
        alarmLogs.add(alarmLog);
    }

    public static Alarm toEntity(AlarmCreateRequest alarmCreateRequest) {
        return Alarm.builder().
                categoryCode(alarmCreateRequest.getCategoryCode())
                .statCode1(alarmCreateRequest.getStatCode1())
                .statCode2(alarmCreateRequest.getStatCode2())
                .abilityCode1(alarmCreateRequest.getAbilityCode1())
                .abilityCode2(alarmCreateRequest.getAbilityCode2())
                .abilityMinValue1(alarmCreateRequest.getAbilityMinValue1())
                .abilityMinValue2(alarmCreateRequest.getAbilityMinValue2())
                .penaltyCode(alarmCreateRequest.getPenaltyCode())
                .penaltyMaxValue(alarmCreateRequest.getPenaltyMinValue())
                .quality(alarmCreateRequest.getQuality())
                .tradeCount(alarmCreateRequest.getTradeCount())
                .price(alarmCreateRequest.getPrice())
                .build();
    }

    public AlarmDataResponse toResponse() {
        return AlarmDataResponse.builder()
                .id(this.id)
                .categoryCode(this.categoryCode)
                .statCode1(this.statCode1)
                .statCode2(this.statCode2)
                .abilityCode1(this.abilityCode1)
                .abilityCode2(this.abilityCode2)
                .abilityMinValue1(this.abilityMinValue1)
                .abilityMinValue2(this.abilityMinValue2)
                .penaltyCode(this.penaltyCode)
                .penaltyMaxValue(this.penaltyMaxValue)
                .quality(this.quality)
                .tradeCount(this.tradeCount)
                .price(this.price)
                .build();
    }

    public RequestAuctionItems toSearchOption() {
        SearchDetailOption statOption = new SearchDetailOption();
        SearchDetailOption statOption2 = new SearchDetailOption();

        statOption.setFirstOption(2);
        statOption.setSecondOption(statCode1);
        statOption2.setFirstOption(2);
        statOption2.setSecondOption(statCode2);

        SearchDetailOption skillOption1 = new SearchDetailOption();
        skillOption1.setFirstOption(3);
        skillOption1.setSecondOption(abilityCode1);
        skillOption1.setMinValue(abilityMinValue1);

        SearchDetailOption skillOption2 = new SearchDetailOption();
        skillOption2.setFirstOption(3);
        skillOption2.setSecondOption(abilityCode2);
        skillOption2.setMinValue(abilityMinValue2);

        SearchDetailOption penalty = new SearchDetailOption();
        penalty.setFirstOption(3);
        penalty.setSecondOption(penaltyCode);
        penalty.setMinValue(penaltyMaxValue);

        List<SearchDetailOption> etcOptions = List.of(statOption, statOption2, skillOption1, skillOption2, penalty);

        return RequestAuctionItems.builder()
                .categoryCode(this.categoryCode)
                .etcOptions(etcOptions)
                .itemGradeQuality(quality)
                .build();
    }
}

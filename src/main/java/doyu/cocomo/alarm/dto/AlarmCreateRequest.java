package doyu.cocomo.alarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AlarmCreateRequest {
    private int categoryCode;
    private Integer statCode1;
    private Integer statCode2;

    private Integer abilityCode1;
    private Integer abilityMinValue1;
    private Integer abilityCode2;
    private Integer abilityMinValue2;
    private Integer penaltyCode;
    private Integer penaltyMinValue;
    private Integer quality;
    private Integer tradeCount;
    private Integer price;


}

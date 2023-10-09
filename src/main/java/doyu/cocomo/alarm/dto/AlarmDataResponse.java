package doyu.cocomo.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AlarmDataResponse {

    private Long id;
    private Integer categoryCode;
    private Integer statCode1;
    private Integer statCode2;

    private Integer abilityCode1;
    private Integer abilityMinValue1;
    private Integer abilityCode2;
    private Integer abilityMinValue2;
    private Integer penaltyCode;
    private Integer penaltyMaxValue;
    private Integer quality;
    private Integer tradeCount;
    private Integer price;
}

package doyu.cocomo.auction.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
public class SearchDetailOption {
    private Integer firstOption;
    private Integer secondOption;
    private Integer minValue;
    private Integer maxValue;
}

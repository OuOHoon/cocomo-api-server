package doyu.cocomo.auction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class AuctionInfo {

    @JsonProperty("StartPrice")
    private Long startPrice;
    @JsonProperty("BuyPrice")
    private Long buyPrice;
    @JsonProperty("BidPrice")
    private Long bidPrice;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("BidCount")
    private Integer bidCount;
    @JsonProperty("BidStartPrice")
    private Long bidStartPrice;
    @JsonProperty("IsCompetitive")
    private Boolean isCompetitive;
    @JsonProperty("TradeAllowCount")
    private Integer tradeAllowCount;
}
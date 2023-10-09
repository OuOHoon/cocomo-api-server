package doyu.cocomo.auction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Auction {

    @JsonProperty("PageNo")
    private int pageNo;
    @JsonProperty("PageSize")
    private int pageSize;
    @JsonProperty("TotalCount")
    private int totalCount;
    @JsonProperty("Items")
    private List<AuctionItem> items;
}

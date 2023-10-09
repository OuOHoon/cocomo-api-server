package doyu.cocomo.alarm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import doyu.cocomo.auction.model.AuctionItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class DiscordAlarmRequest {

    @JsonProperty("user_id")
    private Long discordId;

    @JsonProperty("items")
    private List<AuctionItem> items;
}

package doyu.cocomo.auction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class AuctionItem {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Grade")
    private String grade;
    @JsonProperty("Tier")
    private Integer tier;
    @JsonProperty("Level")
    private Integer level;
    @JsonProperty("Icon")
    private String icon;
    @JsonProperty("GradeQuality")
    private Integer gradeQuality;
    @JsonProperty("AuctionInfo")
    private AuctionInfo auctionInfo;
    @JsonProperty("Options")
    private List<ItemOption> options;
}

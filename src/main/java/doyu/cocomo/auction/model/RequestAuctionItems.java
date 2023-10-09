package doyu.cocomo.auction.model;

import lombok.*;

import java.util.List;

@Data
@Builder
public class RequestAuctionItems {

    private int itemLevelMin;
    private int itemLevelMax;
    private int itemGradeQuality;

    private List<SearchDetailOption> skillOptions;
    private List<SearchDetailOption> etcOptions;
    private ItemSort sort = ItemSort.BUY_PRICE;
    private int categoryCode;
    private String characterClass;
    private Integer itemTier;
    private String itemGrade;
    private String itemName;
    private int pageNo;
    private SortCondition sortCondition = SortCondition.ASC;
}

enum ItemSort {
    BIDSTART_PRICE,
    BUY_PRICE,
    EXPIREDATE,
    ITEM_GRADE,
    ITEM_LEVEL,
    ITEM_QUALITY
}
enum SortCondition {
    ASC,
    DESC
}
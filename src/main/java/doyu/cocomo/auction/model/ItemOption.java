package doyu.cocomo.auction.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ItemOption {
    public enum Type {
        None,
        SKILL,
        STAT,
        ABILITY_ENGRAVE,
        BRACELET_SPECIAL_EFFECTS,
        GEM_SKILL_COOLDOWN_REDUCTION,
        GEM_SKILL_COOLDOWN_REDUCTION_IDENTITY,
        GEM_SKILL_DAMAGE,
        GEM_SKILL_DAMAGE_IDENTITY,
        BRACELET_RANDOM_SLOT
    }

    @JsonProperty("Type")
    private Type type;
    @JsonProperty("OptionName")
    private String optionName;
    @JsonProperty("OptionNameTripod")
    private String optionNameTripod;
    @JsonProperty("Value")
    private Integer value;
    @JsonProperty("IsPenalty")
    private Boolean isPenalty;
    @JsonProperty("ClassName")
    private String className;
}

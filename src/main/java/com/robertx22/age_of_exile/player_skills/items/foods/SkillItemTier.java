package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public enum SkillItemTier {

    TIER0("Spiritual", LevelRanges.STARTER, 0, 0, 1, Formatting.YELLOW, 60 * 5, 10),
    TIER1("Celestial", LevelRanges.LOW, 0.2F, 1, 1.25F, Formatting.AQUA, 60 * 6, 20),
    TIER2("Empyrean", LevelRanges.MIDDLE, 0.4F, 2, 1.5F, Formatting.GOLD, 60 * 7, 30),
    TIER3("Angelic", LevelRanges.HIGH, 0.6F, 3, 1.75F, Formatting.LIGHT_PURPLE, 60 * 8, 40),
    TIER4("Divine", LevelRanges.ENDGAME, 0.8F, 4, 2, Formatting.DARK_PURPLE, 60 * 10, 50);

    SkillItemTier(String word, LevelRange levelRange, float lvl_req, int tier, float statMulti, Formatting format, int durationseconds, float percent_healed) {
        this.word = word;
        this.tier = tier;
        this.statMulti = statMulti;
        this.format = format;
        this.percent_healed = percent_healed;
        this.durationSeconds = durationseconds;
        this.lvl_req = lvl_req;
        this.levelRange = levelRange;
    }

    public int getDisplayTierNumber() {
        return tier + 1;
    }

    public static SkillItemTier of(int tier) {
        return Arrays.stream(SkillItemTier.values())
            .filter(x -> x.tier == tier)
            .findAny()
            .orElse(SkillItemTier.TIER0);
    }

    public static SkillItemTier of(LevelRange lvl) {
        return Arrays.stream(SkillItemTier.values())
            .filter(x -> x.levelRange == lvl)
            .findAny()
            .orElse(SkillItemTier.TIER0);
    }

    public SkillItemTier lowerTier() {

        if (this == TIER0) {
            return null;
        }
        if (this == TIER1) {
            return TIER0;
        }
        if (this == TIER2) {
            return TIER1;
        }
        if (this == TIER3) {
            return TIER2;
        }
        if (this == TIER4) {
            return TIER3;
        }
        return null;

    }

    public SkillItemTier higherTier() {

        if (this == TIER0) {
            return TIER1;
        }
        if (this == TIER1) {
            return TIER2;
        }
        if (this == TIER2) {
            return TIER3;
        }
        if (this == TIER3) {
            return TIER4;
        }
        if (this == TIER4) {
            return null;
        }
        return null;

    }

    public LevelRange levelRange;
    public float lvl_req;
    public String word;
    public int tier;
    public float statMulti;
    public Formatting format;
    public int durationSeconds;
    public float percent_healed;

}

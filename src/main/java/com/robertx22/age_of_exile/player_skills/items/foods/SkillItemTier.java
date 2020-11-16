package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public enum SkillItemTier {

    SPIRITUAL("Spiritual", LevelRanges.STARTER, 0, 0, 1, Formatting.YELLOW, 60 * 2, 20),
    CELESTIAL("Celestial", LevelRanges.LOW, 0.2F, 1, 1.25F, Formatting.AQUA, 60 * 3, 25),
    EMPYREAN("Empyrean", LevelRanges.MIDDLE, 0.4F, 2, 1.5F, Formatting.GOLD, 60 * 5, 30),
    ANGELIC("Angelic", LevelRanges.HIGH, 0.6F, 3, 1.75F, Formatting.LIGHT_PURPLE, 60 * 7, 40),
    DIVINE("Divine", LevelRanges.ENDGAME, 0.8F, 4, 2, Formatting.DARK_PURPLE, 60 * 10, 50);

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

    public static SkillItemTier of(int tier) {
        return Arrays.stream(SkillItemTier.values())
            .filter(x -> x.tier == tier)
            .findAny()
            .orElse(SkillItemTier.SPIRITUAL);
    }

    public SkillItemTier lowerTier() {

        if (this == SPIRITUAL) {
            return null;
        }
        if (this == CELESTIAL) {
            return SPIRITUAL;
        }
        if (this == EMPYREAN) {
            return CELESTIAL;
        }
        if (this == ANGELIC) {
            return EMPYREAN;
        }
        if (this == DIVINE) {
            return ANGELIC;
        }
        return null;

    }

    public SkillItemTier higherTier() {

        if (this == SPIRITUAL) {
            return CELESTIAL;
        }
        if (this == CELESTIAL) {
            return EMPYREAN;
        }
        if (this == EMPYREAN) {
            return ANGELIC;
        }
        if (this == ANGELIC) {
            return DIVINE;
        }
        if (this == DIVINE) {
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
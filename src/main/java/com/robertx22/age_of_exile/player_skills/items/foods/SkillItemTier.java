package com.robertx22.age_of_exile.player_skills.items.foods;

import net.minecraft.util.Formatting;

import java.util.Arrays;

public enum SkillItemTier {

    SPIRITUAL("Spiritual", 0, 1, Formatting.YELLOW, 60 * 2, 20),
    CELESTIAL("Celestial", 1, 1.25F, Formatting.AQUA, 60 * 3, 25),
    EMPYREAN("Empyrean", 2, 1.5F, Formatting.GOLD, 60 * 5, 30),
    ANGELIC("Angelic", 3, 1.75F, Formatting.LIGHT_PURPLE, 60 * 7, 40),
    DIVINE("Divine", 4, 2, Formatting.DARK_PURPLE, 60 * 10, 50);

    SkillItemTier(String word, int tier, float statMulti, Formatting format, int durationseconds, float percent_healed) {
        this.word = word;
        this.tier = tier;
        this.statMulti = statMulti;
        this.format = format;
        this.percent_healed = percent_healed;
        this.durationSeconds = durationseconds;
    }

    public static SkillItemTier of(int amplifier) {
        return Arrays.stream(SkillItemTier.values())
            .filter(x -> x.tier == amplifier - 1)
            .findAny()
            .orElse(SkillItemTier.SPIRITUAL);
    }

    public String word;
    public int tier;
    public float statMulti;
    public Formatting format;
    public int durationSeconds;
    public float percent_healed;

}

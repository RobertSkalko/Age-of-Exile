package com.robertx22.age_of_exile.vanilla_mc.items.foods;

import net.minecraft.util.Formatting;

import java.util.Arrays;

public enum FoodTier {

    SPIRITUAL("Spiritual", 0, 1, Formatting.YELLOW, 60 * 2),
    CELESTIAL("Celestial", 1, 1.25F, Formatting.AQUA, 60 * 3),
    EMPYREAN("Empyrean", 2, 1.5F, Formatting.GOLD, 60 * 5),
    ANGELIC("Angelic", 3, 1.75F, Formatting.LIGHT_PURPLE, 60 * 7),
    Divine("Divine", 4, 2, Formatting.DARK_PURPLE, 60 * 10);

    public String word;

    FoodTier(String word, int tier, float statMulti, Formatting format, int durationseconds) {
        this.word = word;
        this.tier = tier;
        this.statMulti = statMulti;
        this.format = format;
        this.durationSeconds = durationseconds;
    }

    public static FoodTier of(int amplifier) {
        return Arrays.stream(FoodTier.values())
            .filter(x -> x.tier == amplifier - 1)
            .findAny()
            .orElse(FoodTier.SPIRITUAL);
    }

    public int durationSeconds;
    public Formatting format;

    public float statMulti;
    public int tier;

}

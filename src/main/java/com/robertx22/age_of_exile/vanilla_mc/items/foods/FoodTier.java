package com.robertx22.age_of_exile.vanilla_mc.items.foods;

public enum FoodTier {

    SPIRITUAL("Spiritual", 0, 1),
    CELESTIAL("Celestial", 1, 1.25F),
    EMPYREAN("Empyrean", 2, 1.5F),
    ANGELIC("Angelic", 3, 1.75F),
    Divine("Divine", 0, 2);

    public String word;

    FoodTier(String word, int tier, float statMulti) {
        this.word = word;
        this.tier = tier;
        this.statMulti = statMulti;
    }

    public float statMulti;
    public int tier;

}

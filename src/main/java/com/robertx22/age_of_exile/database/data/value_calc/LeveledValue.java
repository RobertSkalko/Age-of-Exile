package com.robertx22.age_of_exile.database.data.value_calc;

public class LeveledValue {
    public final float min;
    public final float max;

    public LeveledValue(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float getValue(LevelProvider provider) {
        if (min == max) {
            return min;
        }

        int maxlevel = provider.getMaxLevel();
        int level = provider.getCurrentLevel();

        float perlevel = (max - min) / maxlevel;
        return min + (perlevel * level);
    }

}

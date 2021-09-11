package com.robertx22.age_of_exile.database.data.value_calc;

public class LeveledValue {
    public final float min;
    public final float max;

    public LeveledValue(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public float getValue(int level, int maxlevel) {
        float perlevel = (max - min) / maxlevel;
        return min + (perlevel * level);
    }

}

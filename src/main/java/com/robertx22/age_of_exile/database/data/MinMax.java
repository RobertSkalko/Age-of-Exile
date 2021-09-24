package com.robertx22.age_of_exile.database.data;

import com.robertx22.library_of_exile.utils.RandomUtils;

public class MinMax {

    public int min;
    public int max;

    public MinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(int num) {

        if (num >= min) {
            if (num <= max) {
                return true;
            }
        }
        return false;

    }

    public int random() {
        return RandomUtils.RandomRange(min, max);
    }

}

package com.robertx22.age_of_exile.saveclasses.wrapped_primitives;

public class StatPercent {

    private final int percent;

    public StatPercent(int percent) {
        this.percent = percent;

        if (percent > 100 || percent < 0) {
            throw new RuntimeException("Percents Must be in between 0-100");
        }
    }

    public int get() {
        return percent;
    }
}

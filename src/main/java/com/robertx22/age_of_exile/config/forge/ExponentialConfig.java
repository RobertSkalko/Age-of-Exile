package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;

public class ExponentialConfig {

    static public float LVL_ONE = 1F;
    static public float MAX_LVL = 100;

    public float scale(float val, int lvl) {

        float lvlmulti = LevelUtils.getMaxLevelMultiplier(lvl);

        float multi = LVL_ONE * (1F - lvlmulti) + MAX_LVL * lvlmulti;

        return multi * val;
    }

    public static void test() {// TODO TEST

        for (int i = 1; i < 50; i++) {

            float lvlmulti = LevelUtils.getMaxLevelMultiplier(i);

            float min = LVL_ONE;

            float max = (MAX_LVL - LVL_ONE) * lvlmulti;

            float total = min + max;

            System.out.print("\n lvl: " + i + ": " + total);
        }

    }

}

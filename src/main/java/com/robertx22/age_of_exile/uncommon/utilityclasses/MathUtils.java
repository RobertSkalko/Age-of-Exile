package com.robertx22.age_of_exile.uncommon.utilityclasses;

public class MathUtils {

    public static float applyResistMultiplier(float value, float percent) {
        float multi = 1F + -percent / 100F;// if its negative it should increase the damage
        // minus before percent cus the logic is inverted. 50% resist means reduce damage, -50% resist mean increase damage.
        return value * multi;
    }

}

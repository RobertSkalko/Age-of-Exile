package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.level_based_numbers;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.saveclasses.spells.IAbility;
import net.minecraft.util.math.MathHelper;

public class LevelBased {

    private float levelOne;
    private float maxLevel;

    private float min = 0;
    private float max = Integer.MAX_VALUE;

    public LevelBased(float levelOne, float maxLevel) {
        this.levelOne = levelOne;
        this.maxLevel = maxLevel;
    }

    public static LevelBased empty() {
        return new LevelBased(ERROR, ERROR);
    }

    public void multiplyBy(float multi) {
        this.levelOne *= multi;
        this.maxLevel *= multi;
    }

    static float ERROR = Integer.MIN_VALUE;

    public boolean isEmpty() {
        return min == ERROR || max == ERROR; // unsure how to use this
    }

    public float get(PlayerSpellCap.ISpellsCap cap, IAbility ability) {
        return get(1, ability);
    }

    public float get(int abilityLevel, IAbility ability) {

        if (isEmpty()) {
            return ERROR;
        }

        if (isEmpty()) {
            try {
                throw new RuntimeException("Trying to use empty value!!!");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        if (levelOne == maxLevel) {
            return maxLevel;
        }

        float multi = 1; // todo

        float val = levelOne + ((maxLevel - levelOne) * multi);

        return MathHelper.clamp(val, min, max);

    }

    public void modifyBy(LevelBased other) {
        this.levelOne = levelOne + other.levelOne;
        this.maxLevel = maxLevel + other.maxLevel;
    }

    public LevelBased min(float min) {
        this.min = min;
        return this;
    }

}

package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class ScalingPlayerDiffData {

    @Store
    private float diff = 0;
    @Store
    private int dcd = 0; // death cooldown

    public List<ITextComponent> getTooltip() {

        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent("Difficulty: " + (int) diff));
        list.add(new StringTextComponent("Extra Mob Levels: " + getBonusLevels()));
        list.add(new StringTextComponent("Mob Damage at max LVL: " + NumberUtils.singleDigitFloat(getDMGMulti(getMobDifficultyAdder(GameBalanceConfig.get().MAX_LEVEL))) + "x"));
        list.add(new StringTextComponent("Mob Health at max LVL: " + NumberUtils.singleDigitFloat(getDMGMulti(getMobDifficultyAdder(GameBalanceConfig.get().MAX_LEVEL))) + "x"));

        list.add(new StringTextComponent(""));

        list.add(new StringTextComponent("Difficulty increases by:"));
        list.add(new StringTextComponent("- time passing"));
        list.add(new StringTextComponent("- hostile mob kills"));

        return list;
    }

    public static float getHPMulti(float mobScalingDiff) {
        return (float) (1 + mobScalingDiff * ServerContainer.get().MOB_HP_MULTI_PER_DIFFICULTY.get());
    }

    public static float getDMGMulti(float mobScalingDiff) {
        return (float) (1 + mobScalingDiff * ServerContainer.get().MOB_DMG_MULTI_PER_DIFFICULTY.get());
    }

    public static float getLootMulti(float mobScalingDiff) {
        return (float) (1 + mobScalingDiff * ServerContainer.get().MOB_LOOT_MULTI_PER_DIFFICULTY.get());
    }

    public float getMobDifficultyAdder(int originalMobLevel) {
        return MathHelper.clamp(diff - (ServerContainer.get().MAX_DIFFICULTY.get()
            .floatValue() * (1F - LevelUtils.getMaxLevelMultiplier(originalMobLevel))), 0, Integer.MAX_VALUE);
    }

    public boolean canAddDeathDifficulty() {
        return dcd < 1;
    }

    public void tickDeathCooldown(int ticks) {
        if (dcd > 0) {
            dcd -= ticks;
        }
    }

    public int getBonusLevels() {
        return (int) (GameBalanceConfig.get().MAX_LEVEL * (diff / ServerContainer.get().MAX_DIFFICULTY.get()));
    }

    public float getDifficulty() {
        return diff;
    }

    public void add(float d) {
        diff += d;
        diff = MathHelper.clamp(diff, 0, ServerContainer.get().MAX_DIFFICULTY.get()
            .floatValue());
    }

}

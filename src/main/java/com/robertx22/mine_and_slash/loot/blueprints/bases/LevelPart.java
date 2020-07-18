package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import net.minecraft.util.math.MathHelper;

public class LevelPart extends BlueprintPart<Integer> {

    public boolean LevelRange = true;

    public int LevelVariance = 3;

    public int minLevel = 1;

    public int maxLevel = Integer.MAX_VALUE;

    public int number;

    public LevelPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected Integer generateIfNull() {

        int finalLvl = number;

        if (LevelRange) {

            finalLvl = RandomUtils.RandomRange(number - LevelVariance, number + LevelVariance);

        }
        return MathHelper.clamp(finalLvl, this.minLevel, ModConfig.INSTANCE.Server.MAX_LEVEL.get());
    }
}
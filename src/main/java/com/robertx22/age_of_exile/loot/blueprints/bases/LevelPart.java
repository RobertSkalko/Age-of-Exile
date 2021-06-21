package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.util.math.MathHelper;

public class LevelPart extends BlueprintPart<Integer, ItemBlueprint> {

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

        if (blueprint instanceof GearBlueprint) {
            GearBlueprint gearb = (GearBlueprint) blueprint;

            minLevel = gearb.gearItemSlot.get()
                .getLevelRange()
                .getMinLevel() + blueprint.extraLevelModifier;

            maxLevel = gearb.gearItemSlot.get()
                .getLevelRange()
                .getMaxLevel() + blueprint.extraLevelModifier;
        }

        if (LevelRange) {
            finalLvl = RandomUtils.RandomRange(number - LevelVariance, number + LevelVariance);
        }

        finalLvl = MathHelper.clamp(finalLvl, minLevel, maxLevel);

        return MathHelper.clamp(finalLvl, this.minLevel, GameBalanceConfig.get().MAX_LEVEL);
    }

}
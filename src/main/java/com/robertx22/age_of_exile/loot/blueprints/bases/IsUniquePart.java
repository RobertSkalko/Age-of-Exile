package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class IsUniquePart extends BlueprintPart<Boolean> {

    public IsUniquePart(GearBlueprint blueprint) {
        super(blueprint);
    }

    public float chance = 0.5F;

    public void setupChances(LootInfo info) {

        if (info.isChestLoot) {
            chance += 2; // todo make configurable
        }
        if (info.world != null) {
            chance *= SlashRegistry.getDimensionConfig(info.world).unique_gear_drop_multi;
        }

        if (info.playerData != null) {
            chance *= info.playerData.getUnit()
                .getCalculatedStat(MagicFind.getInstance())
                .getMultiplier();
        }
    }

    @Override
    protected Boolean generateIfNull() {
        return RandomUtils.roll(chance);
    }
}

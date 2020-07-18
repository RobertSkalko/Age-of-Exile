package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.database.data.stats.types.loot.MagicFind;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

public class IsUniquePart extends BlueprintPart<Boolean> {

    public IsUniquePart(GearBlueprint blueprint) {
        super(blueprint);
    }

    public float chance = 0.25F;

    public void setupChances(LootInfo info) {

        if (info.isChestLoot) {
            chance += 2; // todo make configurable
        }
        if (info.world != null) {
            chance *= SlashRegistry.getDimensionConfig(info.world).unique_gear_drop_multi;
        }

        if (info.playerData != null) {
            chance *= info.playerData.getUnit()
                .peekAtStat(MagicFind.getInstance())
                .getMultiplier();
        }
    }

    @Override
    protected Boolean generateIfNull() {
        return RandomUtils.roll(chance);
    }
}

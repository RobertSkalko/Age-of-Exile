package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.vanilla_mc.items.loot_crate.LootCrateData;
import net.minecraft.item.ItemStack;

public class RuneLootGen extends BaseLootGen<GearBlueprint> {

    public RuneLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ServerContainer.get().RUNE_DROPRATE.get()
            .floatValue());
    }

    @Override
    public LootType lootType() {
        return LootType.Rune;
    }

    @Override
    public boolean condition() {
        if (info.favorRank != null) {
            if (!info.favorRank.drop_runes) {
                return false;
            }
        }
        return !ExileDB.Runes()
            .getFilterWrapped(x -> this.info.level >= x.getReqLevelToDrop()).list.isEmpty();
    }

    @Override
    public ItemStack generateOne() {

        LootCrateData data = new LootCrateData();
        data.type = LootType.Rune;
        data.tier = info.tier;

        return data.createStack();

    }

}


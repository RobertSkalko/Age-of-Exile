package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.vanilla_mc.items.loot_crate.LootCrateData;
import net.minecraft.item.ItemStack;

public class GemLootGen extends BaseLootGen<GearBlueprint> {

    public GemLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ServerContainer.get().GEM_DROPRATE.get()
            .floatValue());
    }

    @Override
    public LootType lootType() {
        return LootType.Gem;
    }

    @Override
    public boolean condition() {

        if (info.favorRank != null) {
            if (!info.favorRank.drop_gems) {
                return false;
            }
        }

        return !ExileDB.Gems()
            .getFilterWrapped(x -> this.info.level >= x.getReqLevelToDrop()).list.isEmpty();
    }

    @Override
    public ItemStack generateOne() {

        LootCrateData data = new LootCrateData();
        data.type = LootType.Gem;
        data.tier = info.tier;

        return data.createStack();

    }

}

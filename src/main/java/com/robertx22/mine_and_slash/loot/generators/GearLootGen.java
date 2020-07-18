package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class GearLootGen extends BaseLootGen<GearBlueprint> {

    public GearLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return ModConfig.INSTANCE.DropRates.GEAR_DROPRATE.get()
            .floatValue();
    }

    @Override
    public LootType lootType() {
        return LootType.NormalItem;
    }

    @Override
    public ItemStack generateOne() {

        GearBlueprint blueprint = new GearBlueprint(info);

        ItemStack stack = blueprint.createStack();

        return stack;

    }

}

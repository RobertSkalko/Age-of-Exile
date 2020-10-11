package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import net.minecraft.item.ItemStack;

public class GearLootGen extends BaseLootGen<GearBlueprint> {

    public GearLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) ModConfig.get().DropRates.GEAR_DROPRATE;
    }

    @Override
    public LootType lootType() {
        return LootType.NormalItem;
    }

    @Override
    public ItemStack generateOne() {

        GearBlueprint blueprint = new GearBlueprint(info);

        ItemStack stack = blueprint.createStack();

        ItemUtils.tryAnnounceItem(stack, info.killer);

        return stack;

    }

}

package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.JewelData;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.ItemStack;

public class JewelLootGen extends BaseLootGen<GearBlueprint> {

    public JewelLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ModConfig.get().DropRates.JEWEL_DROPRATE);
    }

    @Override
    public LootType lootType() {
        return LootType.NormalItem;
    }

    @Override
    public ItemStack generateOne() {

        JewelData jewel = new JewelData();
        jewel.randomize(info.level);

        ItemStack stack = new ItemStack(RandomUtils.randomFromList(ModRegistry.MISC_ITEMS.ALL_JEWELS));

        jewel.saveToStack(stack);

        return stack;

    }

}

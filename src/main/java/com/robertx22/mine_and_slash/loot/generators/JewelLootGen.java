package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.item_classes.JewelData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
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

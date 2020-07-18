package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class CurrencyLootGen extends BaseLootGen<ItemBlueprint> {

    public CurrencyLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return ModConfig.INSTANCE.DropRates.CURRENCY_DROPRATE.get()
            .floatValue();
    }

    @Override
    public LootType lootType() {
        return LootType.Currency;
    }

    @Override
    public boolean condition() {
        return true;
    }

    @Override
    public ItemStack generateOne() {

        return new ItemStack(SlashRegistry.CurrencyItems()
            .getWrapped()
            .ofTierOrLess(info.tier)
            .random());

    }

}

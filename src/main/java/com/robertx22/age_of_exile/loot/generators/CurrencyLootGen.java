package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class CurrencyLootGen extends BaseLootGen<ItemBlueprint> {

    public CurrencyLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return (float) ModConfig.get().DropRates.CURRENCY_DROPRATE;

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

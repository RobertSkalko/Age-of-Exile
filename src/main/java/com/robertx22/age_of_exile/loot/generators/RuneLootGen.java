package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class RuneLootGen extends BaseLootGen<GearBlueprint> {

    public RuneLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ModConfig.get().DropRates.RUNE_DROPRATE);
    }

    @Override
    public LootType lootType() {
        return LootType.NormalItem;
    }

    @Override
    public boolean condition() {
        if (info.favorRank != null) {
            if (!info.favorRank.drop_runes) {
                return false;
            }
        }
        return !SlashRegistry.Runes()
            .getFilterWrapped(x -> this.info.level >= x.getReqLevel()).list.isEmpty();
    }

    @Override
    public ItemStack generateOne() {

        Rune rune = SlashRegistry.Runes()
            .getFilterWrapped(x -> this.info.level >= x.getReqLevel())
            .random();

        ItemStack stack = new ItemStack(rune.getItem());

        return stack;

    }

}


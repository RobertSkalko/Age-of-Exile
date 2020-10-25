package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import net.minecraft.item.ItemStack;

public class GearLootGen extends BaseLootGen<GearBlueprint> {

    public GearLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        float chance = (float) ModConfig.get().DropRates.GEAR_DROPRATE;

        return chance;
    }

    @Override
    public LootType lootType() {
        return LootType.Gear;
    }

    @Override
    public ItemStack generateOne() {

        GearBlueprint blueprint = new GearBlueprint(info);

        ItemStack stack = blueprint.createStack();

        GearItemData gear = Gear.Load(stack);

        gear.isSalvagable = info.favorRank.can_salvage_loot;

        Gear.Save(stack, gear);

        ItemUtils.tryAnnounceItem(stack, info.player);

        return stack;

    }

}

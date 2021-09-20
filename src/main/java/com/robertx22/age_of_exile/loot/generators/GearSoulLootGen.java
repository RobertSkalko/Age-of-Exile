package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class GearSoulLootGen extends BaseLootGen<GearBlueprint> {

    public GearSoulLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        float chance = (float) ModConfig.get().Server.GEAR_DROPRATE;

        return chance;
    }

    @Override
    public LootType lootType() {
        return LootType.Gear;
    }

    public static ItemStack createSoulBasedOnGear(GearBlueprint blueprint) {
        GearItemData gear = blueprint.createData();

        StatSoulData soul = new StatSoulData();

        soul.rar = gear.rarity;
        soul.slot = gear.GetBaseGearType().gear_slot;
        soul.tier = gear.getTier();

        if (gear.isUnique()) {
            soul.uniq = gear.uniq_id;
        }

        ItemStack stack = soul.toStack();
        return stack;
    }

    @Override
    public ItemStack generateOne() {

        GearBlueprint blueprint = new GearBlueprint(info);

        return createSoulBasedOnGear(blueprint);

        /*
        GearItemData gear = Gear.Load(stack);

        if (gear != null) {

            gear.can_sal = info.favorRank.can_salvage_loot;

            Gear.Save(stack, gear);

        }


        return stack;
        */

    }

}

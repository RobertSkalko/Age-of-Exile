package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.item.ItemStack;

public class GearSoulLootGen extends BaseLootGen<GearBlueprint> {

    public GearSoulLootGen(LootInfo info) {
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

        GearItemData gear = blueprint.createData();

        StatSoulData soul = new StatSoulData();

        soul.rar = gear.rarity;
        soul.slot = gear.GetBaseGearType().gear_slot;
        soul.tier = gear.getTier();

        if (MMORPG.RUN_DEV_TOOLS) {
            soul.rar = IRarity.RUNEWORD_ID;
            UniqueGear uniq = ExileDB.UniqueGears()
                .get(ExileDB.RuneWords()
                    .random().uniq_id);
            soul.slot = uniq.getBaseGearType().gear_slot;
            soul.uniq = uniq.GUID();
        }

        if (gear.isUnique()) {
            soul.uniq = gear.uniq_id;
        }

        ItemStack stack = soul.toStack();

        return stack;

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

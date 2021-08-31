package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
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

        //todo
        GearItemData gear = blueprint.createData();

        StatSoulData soul = new StatSoulData();

        soul.rar = gear.rarity;
        soul.slot = gear.GetBaseGearType().gear_slot;
        soul.tier = gear.getTier();

        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.STAT_SOUL);

        stack.getOrCreateTag()
            .putInt("CustomModelData", gear.GetBaseGearType()
                .getGearSlot().custom_model_data_num);

        StackSaving.STAT_SOULS.saveTo(stack, soul);

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

package com.robertx22.mine_and_slash.loot.generators.util;

import com.google.common.base.Preconditions;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.GearItemEnum;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.BaseStatsData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.UniqueStatsData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.item.ItemStack;

public class GearCreationUtils {

    public static ItemStack CreateStack(GearItemData data) {

        ItemStack stack = new ItemStack(data.getItem());

        Gear.Save(stack, data);

        return stack;

    }

    public static ItemStack CreateStack(GearBlueprint blueprint) {

        GearItemData data = CreateData(blueprint);

        ItemStack stack = new ItemStack(data.getItem());

        Gear.Save(stack, data);

        return stack;

    }

    public static GearItemData CreateData(GearBlueprint blueprint) {

        GearItemEnum gearType = GearItemEnum.NORMAL;

        GearRarity rarity = (GearRarity) blueprint.rarity.get();
        GearItemData data = new GearItemData();

        data.level = blueprint.level.get();
        data.rarity = rarity.Rank();
        data.gear_type = blueprint.gearItemSlot.get()
            .GUID();

        if (blueprint.isUniquePart.get()) {
            if (blueprint.gearItemSlot.get()
                .hasUniqueItemVersions()) {

                IUnique unique = blueprint.uniquePart.get();

                Preconditions.checkNotNull(unique);
                Preconditions.checkArgument(data.gear_type.equals(unique.getBaseGearType()
                    .GUID()));

                gearType = GearItemEnum.UNIQUE;

                data.rarity = IRarity.Unique;
                data.is_unique = true;
                data.unique_id = unique.GUID();
                data.uniqueStats = new UniqueStatsData(unique.GUID());
                data.uniqueStats.RerollFully(data);

            }
        }

        data.baseStats = new BaseStatsData();
        data.baseStats.RerollFully(data);

        data.implicitStats.RerollFully(data);

        if (gearType.canGetAffixes()) {
            data.affixes.randomize(data);
        }

        if (blueprint.unidentifiedPart.get()) {
            data.setIdentified(false);
        }

        return data;
    }
}

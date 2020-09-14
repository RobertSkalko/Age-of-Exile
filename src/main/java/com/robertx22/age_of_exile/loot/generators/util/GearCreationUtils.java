package com.robertx22.age_of_exile.loot.generators.util;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.GearItemEnum;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.BaseStatsData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.UniqueStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
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

        IGearRarity rarity = (IGearRarity) blueprint.rarity.get();
        GearItemData data = new GearItemData();

        data.gear_type = blueprint.gearItemSlot.get()
            .GUID();
        data.level = blueprint.level.get();
        data.rarity = rarity.Rank();

        if (blueprint.isUniquePart.get()) {
            if (blueprint.gearItemSlot.get()
                .hasUniqueItemVersions()) {

                UniqueGear unique = blueprint.uniquePart.get();

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

        data.sockets.create(data);

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

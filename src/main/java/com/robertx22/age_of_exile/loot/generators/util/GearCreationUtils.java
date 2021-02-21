package com.robertx22.age_of_exile.loot.generators.util;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.BaseStatsData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.UniqueStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.item.ItemStack;

public class GearCreationUtils {

    public static ItemStack CreateStack(GearItemData data) {

        ItemStack stack = new ItemStack(data.getItem());

        Gear.Save(stack, data);

        return stack;

    }

    public static GearItemData CreateData(GearBlueprint blueprint) {

        GearRarity rarity = blueprint.rarity.get();
        GearItemData data = new GearItemData();

        data.gear_type = blueprint.gearItemSlot.get()
            .GUID();
        data.level = blueprint.level.get();
        data.rarity = rarity.GUID();

        if (rarity.is_unique_item) {
            if (blueprint.gearItemSlot.get()
                .hasUniqueItemVersions()) {

                UniqueGear unique = blueprint.uniquePart.get();

                Preconditions.checkNotNull(unique);
                Preconditions.checkArgument(data.gear_type.equals(unique.getBaseGearType()
                    .GUID()));

                data.rarity = Database.GearRarities()
                    .get(unique.uniqueRarity)
                    .GUID();

                data.is_unique = true;
                data.unique_id = unique.GUID();
                data.uniqueStats = new UniqueStatsData();
                data.uniqueStats.RerollFully(data);

            } else {
                // error bad
            }
        }

        data.sockets.create(data);

        data.baseStats = new BaseStatsData();
        data.baseStats.RerollFully(data);

        data.implicitStats.RerollFully(data);

        data.affixes.randomize(data);

        if (blueprint.unidentifiedPart.get()) {
            data.setIdentified(false);
        }

        return data;
    }
}

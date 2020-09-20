package com.robertx22.age_of_exile.aoe_data.database.compat_items;

import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class CompatibleItems {
    public static List<CompatibleItem> getAllForSerialization() {
        List<CompatibleItem> items = new ArrayList<>();

        try {

            for (BaseGearType slot : SlashRegistry.GearTypes()
                .getSerializable()
            ) {
                Item item = slot.getItem();

                if (!Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {
                    continue;
                }

                if (Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {

                    CompatibleItem c = new CompatibleItem();
                    c.can_be_salvaged = true;
                    c.item_type = slot.GUID();

                    String id = Registry.ITEM.getId(item)
                        .toString();

                    c.guid = id;
                    c.item_id = id;

                    items.add(c);
                }
            }
            for (UniqueGear uniq : SlashRegistry.UniqueGears()
                .getSerializable()
            ) {
                Item item = uniq.getUniqueItem();

                if (!Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {
                    continue;
                }

                if (Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {

                    CompatibleItem c = new CompatibleItem();
                    c.can_be_salvaged = true;
                    c.item_type = uniq.getBaseGearType()
                        .GUID();

                    c.chance_to_become_unique = 100;
                    c.unique_id = uniq.GUID();

                    String id = Registry.ITEM.getId(item)
                        .toString();

                    c.guid = id;
                    c.item_id = id;

                    items.add(c);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}

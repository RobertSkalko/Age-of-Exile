package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.CompatibleItemGenerator;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class CompatibleItemDataPackLoader extends BaseDataPackLoader<CompatibleItem> {
    public static String ID = "compatible_items";

    public CompatibleItemDataPackLoader() {
        super(SlashRegistryType.COMPATIBLE_ITEM, ID, x -> CompatibleItem.EMPTY.fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new CompatibleItemGenerator(getList(), ID);
    }

    public List<CompatibleItem> getList() {
        List<CompatibleItem> items = new ArrayList<>();

        try {

            for (BaseGearType slot : SlashRegistry.GearTypes()
                .getList()
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
                    c.loot_drop_weight = 0;

                    String id = Registry.ITEM.getId(item)
                        .toString();

                    c.guid = id;
                    c.item_id = id;

                    items.add(c);
                }
            }
            for (IUnique uniq : SlashRegistry.UniqueGears()
                .getList()
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
                    c.loot_drop_weight = 0;
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

package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.datapacks.generators.CompatibleItemGenerator;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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

                if (item == Items.AIR || Registry.ITEM.getId(item) == null) {
                    continue;
                }

                if (Registry.ITEM.getId(item)
                    .getNamespace()
                    .equals(Ref.MODID)) {

                    CompatibleItem c = new CompatibleItem();
                    c.can_be_salvaged = true;
                    c.item_type = slot.GUID();
                    c.add_to_loot_drops = false;

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

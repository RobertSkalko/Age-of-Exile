package com.robertx22.mine_and_slash.data_generation.compatible_items;

import com.robertx22.mine_and_slash.data_generation.BaseDataPackManager;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.SlashDataProvider;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class CompatibleItemDataPackManager extends BaseDataPackManager<CompatibleItem> {
    public static String ID = "compatible_items";

    public CompatibleItemDataPackManager() {
        super(SlashRegistryType.COMPATIBLE_ITEM, ID, x -> CompatibleItem.EMPTY.fromJson(x));
    }

    @Override
    public SlashDataProvider getDataPackCreator(DataGenerator gen) {
        return new CompatibleItemProvider(gen, getList(), ID);
    }

    public List<CompatibleItem> getList() {
        List<CompatibleItem> items = new ArrayList<>();

        try {

            for (BaseGearType slot : SlashRegistry.GearTypes()
                .getList()
            ) {
                Item item = slot.getItem();

                if (item == Items.AIR || item.getRegistryName() == null) {
                    continue;
                }

                if (item.getRegistryName()
                    .getNamespace()
                    .equals(Ref.MODID)) {

                    CompatibleItem c = new CompatibleItem();
                    c.can_be_salvaged = true;
                    c.item_type = slot.GUID();
                    c.add_to_loot_drops = false;

                    String id = item.getRegistryName()
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

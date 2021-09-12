package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.vanilla_mc.items.loot_crate.LootCrateData;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

import java.util.ArrayList;
import java.util.List;

public class StackSaving {

    public static List<ItemstackDataSaver<? extends ICommonDataItem>> ALL = new ArrayList();

    public static ItemstackDataSaver<GearItemData> GEARS = new ItemstackDataSaver<>("GEAR_ITEM_DATA", GearItemData.class, () -> new GearItemData());
    public static ItemstackDataSaver<StatSoulData> STAT_SOULS = new ItemstackDataSaver<>("stat_soul", StatSoulData.class, () -> new StatSoulData());
    public static ItemstackDataSaver<LootCrateData> LOOT_CRATE = new ItemstackDataSaver<>("loot_crate", LootCrateData.class, () -> new LootCrateData());

    static {
        ALL.add(GEARS);
    }
}

package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

import java.util.ArrayList;
import java.util.List;

public class StackSaving {

    public static List<ItemstackDataSaver<? extends ICommonDataItem>> ALL = new ArrayList();

    public static ItemstackDataSaver<SkillGemData> SKILL_GEMS = new ItemstackDataSaver<>("gem", SkillGemData.class, () -> new SkillGemData());
    public static ItemstackDataSaver<GearItemData> GEARS = new ItemstackDataSaver<>("GEAR_ITEM_DATA", GearItemData.class, () -> new GearItemData());

    static {
        ALL.add(SKILL_GEMS);
        ALL.add(GEARS);
    }
}

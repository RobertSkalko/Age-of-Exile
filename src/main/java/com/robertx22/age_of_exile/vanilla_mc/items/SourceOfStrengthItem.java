package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class SourceOfStrengthItem extends AutoItem {
    public SourceOfStrengthItem() {
        super(new Properties().tab(CreativeTabs.MyModTab));
    }

    @Override
    public String locNameForLangFile() {
        return "Source of Strength";
    }

    @Override
    public String GUID() {
        return "source_of_strength";
    }
}

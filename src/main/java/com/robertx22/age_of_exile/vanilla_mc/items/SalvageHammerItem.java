package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class SalvageHammerItem extends AutoItem {

    public SalvageHammerItem() {
        super(new Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(1));
    }

    @Override
    public String locNameForLangFile() {
        return "Salvage Hammer";
    }

    @Override
    public String GUID() {
        return "salvage_hammer";
    }
}

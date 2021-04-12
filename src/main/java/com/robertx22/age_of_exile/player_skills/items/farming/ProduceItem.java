package com.robertx22.age_of_exile.player_skills.items.farming;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class ProduceItem extends AutoItem {

    transient String name;

    public ProduceItem(String name) {
        super(new Settings().group(CreativeTabs.Professions));
        this.name = name;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return "";
    }
}

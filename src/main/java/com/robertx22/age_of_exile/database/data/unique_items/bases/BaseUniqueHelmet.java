package com.robertx22.age_of_exile.database.data.unique_items.bases;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateHelmetItem;

public final class BaseUniqueHelmet extends PlateHelmetItem {

    public BaseUniqueHelmet(String locname, boolean isunique) {
        super(locname, isunique);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

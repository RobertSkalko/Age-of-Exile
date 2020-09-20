package com.robertx22.age_of_exile.database.data.unique_items.bases;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateChestItem;

public final class BaseUniqueChest extends PlateChestItem {

    public BaseUniqueChest(String locname) {
        super(locname, true);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

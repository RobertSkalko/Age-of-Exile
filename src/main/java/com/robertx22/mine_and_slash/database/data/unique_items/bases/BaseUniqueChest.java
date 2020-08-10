package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateChestItem;

public final class BaseUniqueChest extends PlateChestItem {

    public BaseUniqueChest(String locname, boolean isunique) {
        super(locname, isunique);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

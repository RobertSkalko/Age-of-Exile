package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlatePantsItem;

public final class BaseUniquePantsItem extends PlatePantsItem {

    public BaseUniquePantsItem(String locname, boolean isunique) {
        super(locname, isunique);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

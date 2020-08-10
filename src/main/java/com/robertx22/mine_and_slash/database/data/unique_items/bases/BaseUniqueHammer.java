package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemHammer;

public final class BaseUniqueHammer extends ItemHammer {

    public BaseUniqueHammer(String locname) {
        super(locname);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }

}

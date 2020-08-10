package com.robertx22.mine_and_slash.database.data.unique_items.bases;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemRing;

public final class BaseUniqueRing extends ItemRing {

    public BaseUniqueRing(String locname) {
        super(locname);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}
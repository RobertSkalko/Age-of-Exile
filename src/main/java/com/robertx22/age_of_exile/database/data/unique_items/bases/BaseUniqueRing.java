package com.robertx22.age_of_exile.database.data.unique_items.bases;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;

public final class BaseUniqueRing extends ItemRing {

    public BaseUniqueRing(String locname) {
        super(locname);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}
package com.robertx22.age_of_exile.database.data.unique_items.bases;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemAxe;

public final class BaseUniqueAxe extends ItemAxe {

    public BaseUniqueAxe(String locname) {
        super(locname);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

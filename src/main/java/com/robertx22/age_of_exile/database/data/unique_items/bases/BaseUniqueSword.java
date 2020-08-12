package com.robertx22.age_of_exile.database.data.unique_items.bases;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemSword;

public final class BaseUniqueSword extends ItemSword {

    public BaseUniqueSword(String locname) {
        super(locname);
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

package com.robertx22.age_of_exile.database.data.unique_items.bases;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.DaggerWeapon;

public class BaseUniqueDagger extends DaggerWeapon {

    public BaseUniqueDagger() {
        super();
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

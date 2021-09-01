package com.robertx22.age_of_exile.database.data.unique_items.bases;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.StaffWeapon;

public class BaseUniqueStaff extends StaffWeapon {

    public BaseUniqueStaff() {
        super();
    }

    @Override
    public boolean shouldRegisterLangName() {
        return false;
    }
}

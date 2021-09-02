package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;

public class ScepterWeapon extends ModWeapon {

    VanillaMaterial mat;

    public ScepterWeapon(VanillaMaterial mat) {
        super(mat.toolmat, new Settings().maxDamage(mat.toolmat.getDurability())
            .group(CreativeTabs.MyModTab), WeaponTypes.scepter);
        this.mat = mat;
    }

}

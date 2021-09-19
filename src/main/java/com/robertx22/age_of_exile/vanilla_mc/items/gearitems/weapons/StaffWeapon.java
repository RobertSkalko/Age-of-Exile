package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;

public class StaffWeapon extends ModWeapon implements IAutoModel {
    VanillaMaterial mat;

    public StaffWeapon(VanillaMaterial mat) {
        super(mat.toolmat, new Properties().durability(mat.toolmat.getUses())
            .tab(CreativeTabs.MyModTab), WeaponTypes.staff);
        this.mat = mat;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.handheld(this);
    }
}

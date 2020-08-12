package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class LeatherPantsItem extends BaseArmorItem {

    public LeatherPantsItem(String locname, boolean isunique) {
        super(Type.LEATHER, locname, EquipmentSlot.LEGS, isunique);
    }
}
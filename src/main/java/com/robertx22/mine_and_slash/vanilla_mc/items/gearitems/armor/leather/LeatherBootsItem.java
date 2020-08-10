package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class LeatherBootsItem extends BaseArmorItem {

    public LeatherBootsItem(String locname, boolean isunique) {
        super(Type.LEATHER, locname, EquipmentSlot.FEET, isunique);
    }
}


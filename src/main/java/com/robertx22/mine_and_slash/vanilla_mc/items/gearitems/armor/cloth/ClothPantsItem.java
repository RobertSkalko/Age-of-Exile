package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth;

import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class ClothPantsItem extends BaseArmorItem {

    public ClothPantsItem(String locname, boolean isunique) {
        super(Type.CLOTH, locname, EquipmentSlot.LEGS, isunique);
    }

}

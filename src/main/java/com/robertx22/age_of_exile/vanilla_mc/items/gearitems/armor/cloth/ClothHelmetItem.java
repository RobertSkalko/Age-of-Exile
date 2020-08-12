package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class ClothHelmetItem extends BaseArmorItem {

    public ClothHelmetItem(String locname, boolean isunique) {
        super(Type.CLOTH, locname, EquipmentSlot.HEAD, isunique);
    }

}

package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class PlateHelmetItem extends BaseArmorItem {

    public PlateHelmetItem(String locname, boolean isunique) {
        super(Type.PLATE, locname, EquipmentSlot.HEAD, isunique);
    }
}

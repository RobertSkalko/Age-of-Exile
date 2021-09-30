package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles;

import com.robertx22.age_of_exile.a_libraries.curios.interfaces.IRing;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseBaublesItem;
import net.minecraft.inventory.EquipmentSlotType;

public class ItemRing extends BaseBaublesItem implements IRing {

    VanillaMaterial mat;

    public ItemRing(VanillaMaterial mat) {
        super(new Properties().durability(mat.armormat.getDurabilityForSlot(EquipmentSlotType.CHEST) * 2)
            .tab(CreativeTabs.MyModTab), "Ring");
        this.mat = mat;
    }
}

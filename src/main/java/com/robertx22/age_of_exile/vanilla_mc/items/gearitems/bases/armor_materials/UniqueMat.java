package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;

public class UniqueMat extends BaseMat {

    static int[] damageReductions = new int[]{
        3,
        6,
        8,
        3
    };

    @Override
    public int getDurability(EquipmentSlot slotIn) {
        return (int) (ArmorMaterials.DIAMOND.getDurability(slotIn) * 1.2F);
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slotIn) {
        return damageReductions[slotIn.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public String getName() {
        return "unique";
    }

    @Override
    public float getToughness() {
        return 2;
    }

}

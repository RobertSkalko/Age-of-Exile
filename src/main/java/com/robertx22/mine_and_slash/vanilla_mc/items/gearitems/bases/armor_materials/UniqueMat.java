package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.armor_materials;

import net.minecraft.entity.EquipmentSlot;

public class UniqueMat extends BaseMat {

    static int[] damageReductions = new int[]{
        3,
        6,
        8,
        3
    };

    @Override
    public int getDurability(EquipmentSlot slotIn) {
        return 1500;
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

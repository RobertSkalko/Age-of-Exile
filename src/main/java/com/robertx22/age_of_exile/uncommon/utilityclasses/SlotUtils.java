package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.EquipmentSlot;

public class SlotUtils {

    public static float multiOf(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.FEET) {
            return 0.5F;
        }
        if (slot == EquipmentSlot.CHEST) {
            return 1;
        }
        if (slot == EquipmentSlot.LEGS) {
            return 0.8F;
        }
        return 1;
    }
}

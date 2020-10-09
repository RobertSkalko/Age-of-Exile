package com.robertx22.age_of_exile.capability.bases;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class EntityGears {

    private HashMap<EquipmentSlot, ItemStack> map = new HashMap<>();

    public ItemStack get(EquipmentSlot slot) {
        if (map.isEmpty()) {
            for (EquipmentSlot s : EquipmentSlot.values()) {
                map.put(s, ItemStack.EMPTY);
            }
        }
        return map.get(slot);
    }

    public ItemStack put(EquipmentSlot slot, ItemStack stack) {
        if (map.isEmpty()) {
            for (EquipmentSlot s : EquipmentSlot.values()) {
                map.put(s, ItemStack.EMPTY);
            }
        }
        return map.put(slot, stack);
    }

}

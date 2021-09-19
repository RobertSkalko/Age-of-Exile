package com.robertx22.age_of_exile.capability.bases;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class EntityGears {

    private HashMap<EquipmentSlotType, ItemStack> map = new HashMap<>();

    public ItemStack get(EquipmentSlotType slot) {
        if (map.isEmpty()) {
            for (EquipmentSlotType s : EquipmentSlotType.values()) {
                map.put(s, ItemStack.EMPTY);
            }
        }
        return map.get(slot);
    }

    public ItemStack put(EquipmentSlotType slot, ItemStack stack) {
        if (map.isEmpty()) {
            for (EquipmentSlotType s : EquipmentSlotType.values()) {
                map.put(s, ItemStack.EMPTY);
            }
        }
        return map.put(slot, stack);
    }

}

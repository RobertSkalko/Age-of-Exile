package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.item.ItemStack;

public class RepairUtils {

    public static boolean isItemBroken(ItemStack stack) {
        if (!stack.isDamageable()) {
            return false;
        }
        return stack.getDamage() >= stack.getMaxDamage() - 10;
    }

}

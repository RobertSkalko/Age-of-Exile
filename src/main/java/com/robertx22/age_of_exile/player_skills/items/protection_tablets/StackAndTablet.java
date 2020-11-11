package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import net.minecraft.item.ItemStack;

public class StackAndTablet {
    public ItemStack stack;
    public ProtectionTabletItem tablet;

    public StackAndTablet(ItemStack stack, ProtectionTabletItem tablet) {
        this.stack = stack;
        this.tablet = tablet;
    }
}

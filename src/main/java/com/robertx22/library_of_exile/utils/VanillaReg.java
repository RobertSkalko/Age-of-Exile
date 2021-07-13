package com.robertx22.library_of_exile.utils;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class VanillaReg {

    public static Identifier getId(World world) {
        return world.getRegistryKey()
            .getValue();

    }

    public static Identifier getId(Item item) {
        return Registry.ITEM.getId(item);
    }

}

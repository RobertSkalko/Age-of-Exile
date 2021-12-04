package com.robertx22.library_of_exile.utils;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class VanillaReg {

    public static ResourceLocation getId(World world) {

        return world.dimension()
            .location();

    }

    public static ResourceLocation getId(Item item) {
        return Registry.ITEM.getKey(item);
    }

}

package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MapManager {

    public static ResourceLocation getResourceLocation(World world) {
        ResourceLocation loc = world.dimension()
            .location();
        return loc;
    }
}

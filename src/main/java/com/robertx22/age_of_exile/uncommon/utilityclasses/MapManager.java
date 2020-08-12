package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MapManager {

    public static Identifier getResourceLocation(World world) {
        Identifier loc = world.getDimensionRegistryKey()
            .getValue();
        return loc;
    }
}

package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MapManager {

    public static Identifier getResourceLocation(World world) {
        Identifier loc = world.getDimensionRegistryKey()
            .getValue();
        return loc;
    }
}

package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

public class MapManager {

    public static Identifier getResourceLocation(DimensionType type) {
        Identifier loc = DimensionType.getId(type);
        return loc;
    }
}

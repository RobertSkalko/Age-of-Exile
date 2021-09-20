package com.robertx22.age_of_exile.dimension;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.util.registry.Registry;

public class DimensionInit {

    public static void init() {
        DungeonDimensionJigsaw.initStatics();

        Registry.register(Registry.CHUNK_GENERATOR, SlashRef.id("dungeon_flat"), VoidChunkGenerator.VOID_CODED);
    }

}

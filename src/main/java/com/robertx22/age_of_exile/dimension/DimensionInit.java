package com.robertx22.age_of_exile.dimension;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.util.registry.Registry;

public class DimensionInit {

    public static void init() {
        DungeonDimensionJigsaw.initStatics();

        Registry.register(Registry.CHUNK_GENERATOR, Ref.id("dungeon_flat"), VoidChunkGenerator.VOID_CODED);
    }

}

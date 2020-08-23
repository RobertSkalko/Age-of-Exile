package com.robertx22.age_of_exile.world_gen.jigsaw.dungeon;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;

import static net.minecraft.structure.pool.StructurePool.Projection.RIGID;
import static net.minecraft.structure.pool.StructurePoolElement.method_30435;
import static net.minecraft.structure.processor.StructureProcessorLists.EMPTY;

public class DungeonPools {

    public static StructurePool STARTPOOL;

    public static void init() {

    }

    static {

        STARTPOOL = StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/starts"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/starts/start1", EMPTY), 1)
            ), RIGID)

        );

        StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/rooms"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/rooms/room1", EMPTY), 1),
                Pair.of(method_30435(Ref.MODID + ":dungeon/rooms/small_room_campfire", EMPTY), 3)
            ), RIGID)
        );

        StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/near_starts"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/near_starts/near_start1", EMPTY), 1)
            ), RIGID)
        );

        StructurePools.register(new StructurePool(new Identifier(Ref.MODID, "dungeon/halls"), new Identifier("empty"),
            ImmutableList.of(
                Pair.of(method_30435(Ref.MODID + ":dungeon/halls/straight_hall", EMPTY), 1)
            ), RIGID)
        );

    }

}

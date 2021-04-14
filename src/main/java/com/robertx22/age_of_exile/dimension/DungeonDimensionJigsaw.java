package com.robertx22.age_of_exile.dimension;

import com.robertx22.library_of_exile.config_utils.BlackOrWhiteList;
import com.robertx22.world_of_exile.config.FeatureConfig;
import com.robertx22.world_of_exile.main.ModProcessorLists;
import com.robertx22.world_of_exile.main.WOE;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DungeonDimensionJigsaw extends StructureWrapper {

    public static void initStatics() {

    }

    static FeatureConfig config;

    static {
        config = new FeatureConfig(new FeatureConfig.MyStructureConfig(1, 0, 0));
        config.PER_DIM.blackOrWhiteList = BlackOrWhiteList.WHITELIST;
        config.PER_DIM.dimensions.add(DimensionIds.DUNGEON_DIMENSION.toString());
    }

    public DungeonDimensionJigsaw() {
        super(DimensionIds.DUNGEON_STRUCTURE, false, config, GenerationStep.Feature.SURFACE_STRUCTURES);
    }

    @Override
    public ConfiguredStructureFeature createConfiguredFeature() {
        return feature.configure(new StructurePoolFeatureConfig(() -> {
            return this.startPool;
        }, 5));
    }

    @Override
    public StructureFeature createFeature() {
        return new DungeonDimensionJigsawFeature(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructurePool createPoolAndInitPools() {
        AbstractPool startBuilder = new Pool(WOE.id("dungeon/starts"));
        startBuilder.add(WOE.id("dungeon/starts/start"));

        AbstractPool roomBuilder = new Pool(WOE.id("dungeon/rooms"));
        roomBuilder.add(WOE.id("dungeon/halls/hall"), 3);
        roomBuilder.add(WOE.id("dungeon/near_start/triple_hall"));
        roomBuilder.add(WOE.id("dungeon/rooms/room1"));
        roomBuilder.add(WOE.id("dungeon/rooms/room2"));
        roomBuilder.add(WOE.id("dungeon/rooms/room3"));
        roomBuilder.add(WOE.id("dungeon/rooms/pool1"));

        roomBuilder.build();

        AbstractPool stairsBuilder = new Pool(WOE.id("dungeon/stairs"));
        stairsBuilder.add(WOE.id("dungeon/stairs/stairs"));
        stairsBuilder.build();

        return startBuilder.build();
    }

    static class Pool extends AbstractPool {

        public Pool(Identifier poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return ModProcessorLists.INSTANCE.DEFAULT_PROCESSORS;
        }
    }

}

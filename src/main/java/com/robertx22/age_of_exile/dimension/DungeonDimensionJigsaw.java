package com.robertx22.age_of_exile.dimension;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.config_utils.BlackOrWhiteList;
import com.robertx22.world_of_exile.config.FeatureConfig;
import com.robertx22.world_of_exile.main.structures.base.StructureWrapper;
import com.robertx22.world_of_exile.world_gen.AbstractPool;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
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
        }, 4));
    }

    @Override
    public StructureFeature createFeature() {
        return new DungeonDimensionJigsawFeature(StructurePoolFeatureConfig.CODEC);
    }

    @Override
    public StructurePool createPoolAndInitPools() {
        AbstractPool startBuilder = new Pool(Ref.id("dungeon/starts"));
        startBuilder.add(Ref.id("stone/start"));

        AbstractPool stone = new Pool(Ref.id("stone"));
        stone.add(Ref.id("stone/1"));
        stone.add(Ref.id("stone/2"));
        stone.add(Ref.id("stone/3"));
        stone.add(Ref.id("stone/4"));
        stone.add(Ref.id("stone/5"));

        stone.build();

        return startBuilder.build();
    }

    static class Pool extends AbstractPool {

        public Pool(Identifier poolId) {
            super(poolId);
        }

        @Override
        public StructureProcessorList processorList() {
            return StructureProcessorLists.EMPTY;
        }
    }

}

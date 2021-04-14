package com.robertx22.age_of_exile.dimension;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DungeonDimensionJigsawFeature extends JigsawFeature {
    public static int DISTANCE = 10;

    public DungeonDimensionJigsawFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 30, true, false);

    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGen, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, StructurePoolFeatureConfig structurePoolFeatureConfig) {
        return cpos.x % DISTANCE == 0 && cpos.z % DISTANCE == 0;
    }
}


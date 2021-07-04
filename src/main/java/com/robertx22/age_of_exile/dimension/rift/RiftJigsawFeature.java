package com.robertx22.age_of_exile.dimension.rift;

import com.mojang.serialization.Codec;
import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsawFeature;
import com.robertx22.age_of_exile.dimension.VoidChunkGenerator;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class RiftJigsawFeature extends JigsawFeature {

    public static int HEIGHT = 80;

    public RiftJigsawFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 80, true, false);

    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGen, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, StructurePoolFeatureConfig structurePoolFeatureConfig) {

        if (chunkGen instanceof VoidChunkGenerator == false) {
            return false;
        }

        ChunkPos spawn = DungeonDimensionJigsawFeature.getSpawnChunkOf(cpos);

        return spawn.x == cpos.x && spawn.z == cpos.z;
    }
}


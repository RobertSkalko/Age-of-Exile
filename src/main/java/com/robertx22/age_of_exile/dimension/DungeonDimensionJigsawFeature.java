package com.robertx22.age_of_exile.dimension;

import com.ibm.icu.impl.Assert;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DungeonDimensionJigsawFeature extends JigsawFeature {

    public static int HEIGHT = 80;

    public static int DISTANCE = 10;

    public static ChunkPos getSpawnChunkOf(BlockPos p) {
        return getSpawnChunkOf(new ChunkPos(p));
    }

    public static ChunkPos getSpawnChunkOf(ChunkPos cp) {

        int x = cp.x;
        int z = cp.z;

        int remx = x % DISTANCE;
        int remz = z % DISTANCE;

        if (remx >= DISTANCE / 2) {
            x += DISTANCE - remx;
        } else {
            x -= remx;
        }
        if (remz >= DISTANCE / 2) {
            z += DISTANCE - remz;
        } else {
            z -= remz;
        }

        ChunkPos spawn = new ChunkPos(x, z);

        Assert.assrt(spawn.x % DISTANCE == 0 && spawn.z % DISTANCE == 0);

        return spawn;

    }

    public DungeonDimensionJigsawFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 80, true, false);

    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGen, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, StructurePoolFeatureConfig structurePoolFeatureConfig) {
        ChunkPos spawn = getSpawnChunkOf(cpos);

        return spawn.x == cpos.x && spawn.z == cpos.z;
    }
}


package com.robertx22.age_of_exile.dimension;

import com.mojang.serialization.Codec;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ErrorUtils;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class DungeonDimensionJigsawFeature extends JigsawStructure {

    public static int HEIGHT = 80;

    public static int DISTANCE = 30;

    public static ChunkPos getSpawnChunkOf(BlockPos p) {
        return getSpawnChunkOf(new ChunkPos(p));
    }

    public static ChunkPos getSpawnChunkOf(ChunkPos cp) {

        int x = getNearest(cp.x);
        int z = getNearest(cp.z);

        ChunkPos spawn = new ChunkPos(x, z);

        ErrorUtils.ifFalse(spawn.x % DISTANCE == 0 && spawn.z % DISTANCE == 0);

        return spawn;

    }

    static int getNearest(int num) {

        int o = Math.round(num / (float) DISTANCE);
        if (o == 0) {
            o++;
        }
        o *= DISTANCE;
        return o;
    }

    public DungeonDimensionJigsawFeature(Codec<VillageConfig> codec) {
        super(codec, 80, true, false);

    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGen, BiomeProvider biomeSource, long l, SharedSeedRandom chunkRandom, int i, int j, Biome biome, ChunkPos cpos, VillageConfig structurePoolFeatureConfig) {

        if (chunkGen instanceof VoidChunkGenerator == false) {
            return false;
        }

        ChunkPos spawn = getSpawnChunkOf(cpos);

        return spawn.x == cpos.x && spawn.z == cpos.z;
    }
}


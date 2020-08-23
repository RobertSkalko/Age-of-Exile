package com.robertx22.age_of_exile.world_gen.jigsaw.dungeon;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModDungeonFeature extends JigsawFeature {
    public ModDungeonFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, StructurePoolFeatureConfig structurePoolFeatureConfig) {
        return true;
    }
}

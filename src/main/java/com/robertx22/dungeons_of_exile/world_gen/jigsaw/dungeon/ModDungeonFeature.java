package com.robertx22.dungeons_of_exile.world_gen.jigsaw.dungeon;

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
        super(codec, -9, true, true);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, StructurePoolFeatureConfig structurePoolFeatureConfig) {

        return biome.getDepth() < 0.5F && biome.getCategory() != Biome.Category.OCEAN; // no mountains
    }
}


package com.robertx22.age_of_exile.world_gen.towers.processors;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public abstract class BiomeProcessorType {

    public abstract String id();

    public abstract boolean isBiomeGood(Biome biome);

    public abstract Map<Block, Block> getReplaceMap();

}

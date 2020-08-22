package com.robertx22.age_of_exile.world_gen.towers.processors;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class PrismaProc extends BiomeProcessorType {

    public final Map<Block, Block> MAP = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.COBBLESTONE, Blocks.PRISMARINE_BRICKS);
        map.put(Blocks.COBBLESTONE_STAIRS, Blocks.PRISMARINE_BRICK_STAIRS);
        map.put(Blocks.COBBLESTONE_SLAB, Blocks.PRISMARINE_BRICK_SLAB);
    });

    private PrismaProc() {
    }

    public static PrismaProc getInstance() {
        return PrismaProc.SingletonHolder.INSTANCE;
    }

    @Override
    public String id() {
        return "prisma";
    }

    @Override
    public boolean isBiomeGood(Biome biome) {
        return biome.getCategory() == Biome.Category.SWAMP;
    }

    @Override
    public Map<Block, Block> getReplaceMap() {
        return MAP;
    }

    private static class SingletonHolder {
        private static final PrismaProc INSTANCE = new PrismaProc();
    }
}

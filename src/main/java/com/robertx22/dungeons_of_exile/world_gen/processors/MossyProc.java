package com.robertx22.dungeons_of_exile.world_gen.processors;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class MossyProc extends BiomeProcessorType {

    public final Map<Block, Block> MAP = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.COBBLESTONE, Blocks.MOSSY_STONE_BRICKS);
        map.put(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS);
        map.put(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB);
    });

    private MossyProc() {
    }

    public static MossyProc getInstance() {
        return MossyProc.SingletonHolder.INSTANCE;
    }

    @Override
    public String id() {
        return "mossy";
    }

    @Override
    public boolean isBiomeGood(Biome biome) {
        return biome.getCategory() == Biome.Category.JUNGLE;
    }

    @Override
    public Map<Block, Block> getReplaceMap() {
        return MAP;
    }

    private static class SingletonHolder {
        private static final MossyProc INSTANCE = new MossyProc();
    }
}

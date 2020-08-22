package com.robertx22.age_of_exile.world_gen.towers.processors;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class BrickProc extends BiomeProcessorType {

    public final Map<Block, Block> MAP = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.COBBLESTONE, Blocks.STONE_BRICKS);
        map.put(Blocks.COBBLESTONE_STAIRS, Blocks.STONE_BRICK_STAIRS);
        map.put(Blocks.COBBLESTONE_SLAB, Blocks.STONE_BRICK_SLAB);
    });

    private BrickProc() {
    }

    public static BrickProc getInstance() {
        return BrickProc.SingletonHolder.INSTANCE;
    }

    @Override
    public String id() {
        return "brick";
    }

    @Override
    public boolean isBiomeGood(Biome biome) {
        return biome.getCategory() == Biome.Category.FOREST;
    }

    @Override
    public Map<Block, Block> getReplaceMap() {
        return MAP;
    }

    private static class SingletonHolder {
        private static final BrickProc INSTANCE = new BrickProc();
    }
}

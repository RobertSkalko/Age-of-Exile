package com.robertx22.age_of_exile.world_gen.towers.processors;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class SandstoneProc extends BiomeProcessorType {

    public final Map<Block, Block> MAP = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.COBBLESTONE, Blocks.SANDSTONE);
        map.put(Blocks.COBBLESTONE_STAIRS, Blocks.SANDSTONE_STAIRS);
        map.put(Blocks.COBBLESTONE_SLAB, Blocks.SANDSTONE_SLAB);
    });

    private SandstoneProc() {
    }

    public static SandstoneProc getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String id() {
        return "sandstone";
    }

    @Override
    public boolean isBiomeGood(Biome biome) {
        return biome.getCategory() == Biome.Category.DESERT;
    }

    @Override
    public Map<Block, Block> getReplaceMap() {
        return MAP;
    }

    private static class SingletonHolder {
        private static final SandstoneProc INSTANCE = new SandstoneProc();
    }
}

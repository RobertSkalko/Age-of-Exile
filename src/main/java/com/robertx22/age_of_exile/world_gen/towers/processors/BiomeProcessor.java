package com.robertx22.age_of_exile.world_gen.towers.processors;

import com.mojang.serialization.Codec;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class BiomeProcessor extends StructureProcessor {

    public static HashMap<String, BiomeProcessorType> MAP;
    public String typeid;

    public BiomeProcessor(String typeid) {
        this.typeid = typeid;
    }

    static {
        MAP = new HashMap<>();
        MAP.put(SandstoneProc.getInstance()
            .id(), SandstoneProc.getInstance());
    }

    public static final Codec<BiomeProcessor> CODEC = Codec.STRING.fieldOf("type")
        .xmap(BiomeProcessor::new, (blockAgeStructureProcessor) -> {
            return blockAgeStructureProcessor.typeid;
        })
        .codec();

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {

        BiomeProcessorType type = null;

        Biome biome = worldView.getBiome(pos);

        for (BiomeProcessorType t : BiomeProcessor.MAP.values()) {
            if (t.isBiomeGood(biome)) {
                type = t;
                break;
            }
        }

        if (type == null) {
            return blockinfo2;
        }

        Map<Block, Block> map = type.getReplaceMap();

        Block block = (Block) map.get(blockinfo2.state.getBlock());
        if (block == null) {
            return blockinfo2;
        } else {
            BlockState blockState = blockinfo2.state;
            BlockState blockState2 = block.getDefaultState();
            if (blockState.contains(StairsBlock.FACING)) {
                blockState2 = (BlockState) blockState2.with(StairsBlock.FACING, blockState.get(StairsBlock.FACING));
            }

            if (blockState.contains(StairsBlock.HALF)) {
                blockState2 = (BlockState) blockState2.with(StairsBlock.HALF, blockState.get(StairsBlock.HALF));
            }

            if (blockState.contains(SlabBlock.TYPE)) {
                blockState2 = (BlockState) blockState2.with(SlabBlock.TYPE, blockState.get(SlabBlock.TYPE));
            }

            return new Structure.StructureBlockInfo(blockinfo2.pos, blockState2, blockinfo2.tag);
        }
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.BIOME_PROCESSOR;
    }
}

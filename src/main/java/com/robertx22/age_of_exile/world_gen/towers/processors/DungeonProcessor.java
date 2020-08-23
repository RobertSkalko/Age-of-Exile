package com.robertx22.age_of_exile.world_gen.towers.processors;

import com.mojang.serialization.Codec;
import com.robertx22.age_of_exile.mixin_ducks.MobSpawnerLogicDuck;
import com.robertx22.age_of_exile.mixin_ducks.SignDuck;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.WorldView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DungeonProcessor extends StructureProcessor {

    String nothing;

    public DungeonProcessor(String typeid) {

    }

    public static final Codec<DungeonProcessor> CODEC = Codec.STRING.fieldOf("type")
        .xmap(DungeonProcessor::new, (p) -> {
            return p.nothing;
        })
        .codec();

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo blockinfo2, StructurePlacementData structurePlacementData) {
        BlockState currentState = blockinfo2.state;
        BlockState resultState = blockinfo2.state;

        try {
            if (currentState.getBlock() instanceof SignBlock) {

                SignBlockEntity signb = (SignBlockEntity) SignBlockEntity.createFromTag(currentState, blockinfo2.tag);

                SignDuck sign = (SignDuck) signb;

                List<Text> list = Arrays.asList(sign.getText());
                List<String> texts = list.stream()
                    .map(x -> x.getString())
                    .collect(Collectors.toList());

                if (texts.contains("[spawner]")) {
                    resultState = Blocks.SPAWNER.getDefaultState();

                    MobSpawnerBlockEntity spawner = new MobSpawnerBlockEntity();
                    MobSpawnerLogicDuck saccess = (MobSpawnerLogicDuck) spawner.getLogic();
                    saccess.getspawnPotentials()
                        .clear();

                    EntityType type = ModRegistry.ENTITIES.ARCANE_SPIDER;

                    MobSpawnerEntry entry = new MobSpawnerEntry();
                    entry.getEntityTag()
                        .putString("id", Registry.ENTITY_TYPE.getId(type)
                            .toString());

                    saccess.getspawnPotentials()
                        .add(entry);

                    spawner.getLogic()
                        .setSpawnEntry(entry);

                    CompoundTag resultTag = new CompoundTag();
                    spawner.toTag(resultTag);

                    return new Structure.StructureBlockInfo(blockinfo2.pos, resultState, resultTag);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Structure.StructureBlockInfo(blockinfo2.pos, resultState, blockinfo2.tag);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldGen.INSTANCE.SIGN_PROCESSOR;
    }
}


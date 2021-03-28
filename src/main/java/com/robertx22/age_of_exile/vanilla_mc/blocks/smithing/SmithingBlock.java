package com.robertx22.age_of_exile.vanilla_mc.blocks.smithing;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseModificationBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class SmithingBlock extends BaseModificationBlock {

    public SmithingBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SmithingTile();
    }

    @Override
    public Identifier getContainerId() {
        return ModRegistry.CONTAINERS.SMITHING_STATION;
    }
}

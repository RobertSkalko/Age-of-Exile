package com.robertx22.age_of_exile.dimension.rift;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class SummonPortalBlock extends Block implements BlockEntityProvider {

    public SummonPortalBlock() {
        super(AbstractBlock.Settings.of(Material.AIR)
            .nonOpaque()
            .noCollision());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SummonPortalBlockEntity();
    }
}


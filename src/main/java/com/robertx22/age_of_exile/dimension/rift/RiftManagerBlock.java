package com.robertx22.age_of_exile.dimension.rift;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class RiftManagerBlock extends Block implements BlockEntityProvider {

    public RiftManagerBlock() {
        super(AbstractBlock.Settings.of(Material.AIR));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new RiftManagerBlockEntity();
    }
}

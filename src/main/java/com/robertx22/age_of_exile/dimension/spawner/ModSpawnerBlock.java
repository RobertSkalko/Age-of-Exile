package com.robertx22.age_of_exile.dimension.spawner;

import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ModSpawnerBlock extends OpaqueBlock implements BlockEntityProvider {

    public ModSpawnerBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ModSpawnerBlockEntity();
    }

}


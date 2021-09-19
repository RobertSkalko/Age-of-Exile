package com.robertx22.age_of_exile.dimension.spawner;

import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ModSpawnerBlock extends OpaqueBlock implements ITileEntityProvider {

    public ModSpawnerBlock() {
        super(Properties.of(Material.STONE)
            .strength(5F, 2));
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new ModSpawnerBlockEntity();
    }

}


package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseModificationBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class BlockGearSalvage extends BaseModificationBlock {

    public BlockGearSalvage() {
        super(Properties.of(Material.STONE)
            .strength(5F, 2)
            .noOcclusion());
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new TileGearSalvage();
    }

    @Override
    public ResourceLocation getContainerId() {
        return ModRegistry.CONTAINERS.GEAR_SALVAGE;
    }

}
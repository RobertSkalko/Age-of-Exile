package com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseModificationBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

public class RuneWordStationBlock extends BaseModificationBlock {

    public RuneWordStationBlock() {
        super(Properties.of(Material.STONE)
            .strength(5F, 2));
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new RuneWordStationTile();
    }

    @Override
    public ResourceLocation getContainerId() {
        return ModRegistry.CONTAINERS.GEAR_SOCKET;
    }
}
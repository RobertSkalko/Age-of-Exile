package com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station;

import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.bases.BaseInventoryBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class BlockGearSalvage extends BaseInventoryBlock {

    public BlockGearSalvage() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2)
            .nonOpaque());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TileGearSalvage();
    }

    @Override
    public Identifier getContainerId() {
        return ModRegistry.CONTAINERS.GEAR_SALVAGE;
    }

}
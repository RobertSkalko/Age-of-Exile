package com.robertx22.age_of_exile.vanilla_mc.blocks.furnace;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseInventoryBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class EssentiaFurnaceBlock extends BaseInventoryBlock {

    public EssentiaFurnaceBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }

    // TODO

    @Override
    public Identifier getContainerId() {
        return ModRegistry.CONTAINERS.ESSENTIA_FURNACE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EssentiaFurnaceBlockEntity();
    }
}

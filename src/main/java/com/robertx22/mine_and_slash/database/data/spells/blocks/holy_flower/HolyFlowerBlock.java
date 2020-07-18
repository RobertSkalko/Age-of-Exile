package com.robertx22.mine_and_slash.database.data.spells.blocks.holy_flower;

import com.robertx22.mine_and_slash.database.data.spells.blocks.base.BaseSpellBlock;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class HolyFlowerBlock extends BaseSpellBlock {
    public static final String ID = Ref.MODID + ":holy_flower";

    public HolyFlowerBlock() {
        super();

    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockView world) {
        return new HolyFlowerTileEntity();
    }
}

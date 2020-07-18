package com.robertx22.mine_and_slash.database.data.spells.blocks.thorn_bush;

import com.robertx22.mine_and_slash.database.data.spells.blocks.base.BaseSpellBlock;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ThornBushBlock extends BaseSpellBlock {
    public static final String ID = Ref.MODID + ":thorn_bush";

    public ThornBushBlock() {
        super();

    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ThornBushTileEntity();
    }
}

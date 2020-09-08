package com.robertx22.age_of_exile.mixin_ducks;

import net.minecraft.block.BlockState;

public interface FallingBlockAccessor {

    void setDestroyedOnLanding(boolean bool);

    void setBlockState(BlockState bool);

}

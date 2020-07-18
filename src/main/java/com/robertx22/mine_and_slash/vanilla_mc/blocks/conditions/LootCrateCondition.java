package com.robertx22.mine_and_slash.vanilla_mc.blocks.conditions;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public abstract class LootCrateCondition {

    public abstract boolean canOpenCrate(PlayerEntity player, BlockEntity box);

    public abstract Text tellCondition();
}

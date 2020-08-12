package com.robertx22.age_of_exile.vanilla_mc.blocks.conditions;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;

public abstract class LootCrateCondition {

    public abstract boolean canOpenCrate(PlayerEntity player, BlockEntity box);

    public abstract MutableText tellCondition();
}

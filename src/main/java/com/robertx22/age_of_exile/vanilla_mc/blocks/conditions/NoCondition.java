package com.robertx22.age_of_exile.vanilla_mc.blocks.conditions;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class NoCondition extends LootCrateCondition {

    @Override
    public boolean canOpenCrate(PlayerEntity player, BlockEntity box) {
        return true;
    }

    @Override
    public MutableText tellCondition() {
        return new LiteralText("");
    }
}

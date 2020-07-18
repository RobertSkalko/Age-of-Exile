package com.robertx22.mine_and_slash.vanilla_mc.blocks.conditions;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class NoCondition extends LootCrateCondition {

    @Override
    public boolean canOpenCrate(PlayerEntity player, BlockEntity box) {
        return true;
    }

    @Override
    public Text tellCondition() {
        return new LiteralText("");
    }
}

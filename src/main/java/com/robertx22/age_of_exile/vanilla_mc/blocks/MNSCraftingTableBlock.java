package com.robertx22.age_of_exile.vanilla_mc.blocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;

public class MNSCraftingTableBlock extends CraftingTableBlock {

    public static boolean IS_USING = false;

    public static boolean isPlayerUsing(PlayerEntity player) {
        return IS_USING;
    }

    public MNSCraftingTableBlock() {
        super(Properties.copy(Blocks.CRAFTING_TABLE));
    }

}

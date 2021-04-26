package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class OnTickGiveTpBack {

    public static void give(PlayerEntity player) {

        if (WorldUtils.isDungeonWorld(player.world)) {
            if (player.inventory.count(ModRegistry.MISC_ITEMS.TELEPORT_BACK) < 1) {
                PlayerUtils.giveItem(new ItemStack(ModRegistry.MISC_ITEMS.TELEPORT_BACK), player);
            }
        }

    }
}

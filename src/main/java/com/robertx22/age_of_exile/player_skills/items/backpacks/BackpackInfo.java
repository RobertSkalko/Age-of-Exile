package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BackpackUpgradeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BackpackInfo {

    public BackpackInfo(PlayerEntity player, ItemStack stack) {

        BackpackInventory inv = new BackpackInventory(player, stack);

        for (int i = 0; i < 9; i++) {
            ItemStack up = inv.getStack(i);

            if (up.getItem() instanceof BackpackUpgradeItem) {
                BackpackUpgradeItem upgrade = (BackpackUpgradeItem) up.getItem();
                upgrade.upgrade.upgrade(upgrade, this);
            }

        }

        if (extraRows > 4) {
            extraRows = 4;
        }
        tier = extraRows;
    }

    public int tier = 0;

    public int extraRows = 0;

    public List<AutoPickupType> autoPickups = new ArrayList<>();
}

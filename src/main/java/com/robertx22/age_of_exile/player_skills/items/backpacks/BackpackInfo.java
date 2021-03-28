package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BagUpgradesData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BackpackInfo {

    BagUpgradesData data;

    public BackpackInfo(PlayerEntity player, ItemStack stack) {

        this.data = BagUpgradesData.load(stack);

        data.getUpgrades()
            .forEach(x -> {
                x.upgrade.upgrade(x, this);
            });

        if (extraRows > 4) {
            extraRows = 4;
        }
        tier = extraRows;
    }

    public int tier = 0;

    public int extraRows = 0;

    public List<AutoPickupType> autoPickups = new ArrayList<>();
}

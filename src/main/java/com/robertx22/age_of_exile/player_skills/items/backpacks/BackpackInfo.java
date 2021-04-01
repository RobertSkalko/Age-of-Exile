package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BagUpgradesData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
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
    }

    public int tier = -1;

    public int extraRows = 0;

    public List<AutoPickupType> autoPickups = new ArrayList<>();
    public boolean dontSalvageEnchantedOrNamed = false;
    public List<Integer> tiersToAutoSalvage = new ArrayList<>();

    public boolean canSalvage(ItemStack stack) {

        if (dontSalvageEnchantedOrNamed) {
            if (stack.hasEnchantments() || stack.hasCustomName()) {
                return false;
            }
        }

        GearItemData gear = Gear.Load(stack);

        if (gear != null) {
            if (gear.getRarity()
                .hasTier()) {
                if (tiersToAutoSalvage.contains(gear.getRarity().item_tier)) {
                    return true;
                }
            }
        }

        return false;

    }

}

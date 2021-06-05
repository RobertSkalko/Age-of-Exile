package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BackpackUpgrade;
import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BackpackUpgradeItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BackpackUpgradesRegister extends BaseItemRegistrator {

    public static List<Item> TRASH_ITEMS = Arrays.asList(
        Items.ROTTEN_FLESH, Items.STRING, Items.SPIDER_EYE, Items.BOW, Items.BONE
    );

    public List<BackpackUpgradeItem> ALL = new ArrayList<>();

    public HashMap<SkillItemTier, BackpackUpgradeItem> SIZE = new HashMap<>();

    BackpackUpgradeItem GEM = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_GEM_PICKUP, SkillItemTier.TIER1, "gem", "Auto Gem Pickup Upgrade"));
    BackpackUpgradeItem TRASH = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_TRASH_PICKUP, SkillItemTier.TIER0, "trash", "Auto Trash Pickup Upgrade"));
    BackpackUpgradeItem RUNE = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_RUNE_PICKUP, SkillItemTier.TIER2, "rune", "Auto Rune Pickup Upgrade"));
    BackpackUpgradeItem CURRENCY = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_CURRENCY_PICKUP, SkillItemTier.TIER3, "currency", "Auto Currency Pickup Upgrade"));
    BackpackUpgradeItem ESSENCE = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_ESSENCE_PICKUP, SkillItemTier.TIER0, "essence", "Auto Essence Pickup Upgrade"));

    BackpackUpgradeItem SAVE_NAMED = upgrade(new BackpackUpgradeItem(BackpackUpgrade.SAVE_FROM_SALVAGE, SkillItemTier.TIER1, "save", "Save Important Items Upgrade"));

    BackpackUpgradeItem SAL_0 = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_SALVAGE_0, SkillItemTier.TIER0, "auto_sal", "Auto Salvage Commons Upgrade"));
    BackpackUpgradeItem SAL_1 = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_SALVAGE_1, SkillItemTier.TIER1, "auto_sal", "Auto Salvage Uncommons Upgrade"));
    BackpackUpgradeItem SAL_2 = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_SALVAGE_2, SkillItemTier.TIER2, "auto_sal", "Auto Salvage Rares Upgrade"));

    public BackpackUpgradeItem upgrade(BackpackUpgradeItem item) {
        ALL.add(item);
        return item(item);
    }

    public BackpackUpgradesRegister() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            SIZE.put(tier, upgrade(new BackpackUpgradeItem(BackpackUpgrade.Size, tier, "size", tier.word + " Size Upgrade")));
        }
    }

}

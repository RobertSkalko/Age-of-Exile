package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BackpackUpgrade;
import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BackpackUpgradeItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackpackUpgradesRegister extends BaseItemRegistrator {

    public List<BackpackUpgradeItem> ALL = new ArrayList<>();

    public HashMap<SkillItemTier, BackpackUpgradeItem> SIZE = new HashMap<>();

    BackpackUpgradeItem GEM = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_GEM_PICKUP, SkillItemTier.TIER1, "gem", "Auto Gem Pickup Upgrade"));
    BackpackUpgradeItem RUNE = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_RUNE_PICKUP, SkillItemTier.TIER2, "gem", "Auto Rune Pickup Upgrade"));
    BackpackUpgradeItem CURRENCY = upgrade(new BackpackUpgradeItem(BackpackUpgrade.AUTO_CURRENCY_PICKUP, SkillItemTier.TIER3, "gem", "Auto Currency Pickup Upgrade"));

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

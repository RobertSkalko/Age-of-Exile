package com.robertx22.age_of_exile.database.data.currency.key;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.item.Item;

public class TenTierIncrease extends IncreaseDungeonKeyTier {

    public TenTierIncrease() {
        super();
    }

    @Override
    public String GUID() {
        return "currency/ten_tier";
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Medium Dungeon Key Upgrade";
    }

    @Override
    public int increaseTierBy() {
        return 10;
    }

    @Override
    public Item craftItem() {
        return ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(SkillItemTier.TIER2);
    }
}


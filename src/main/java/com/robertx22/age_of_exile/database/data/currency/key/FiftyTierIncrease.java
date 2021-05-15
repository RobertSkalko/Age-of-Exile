package com.robertx22.age_of_exile.database.data.currency.key;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.item.Item;

public class FiftyTierIncrease extends IncreaseDungeonKeyTier {

    public FiftyTierIncrease() {
        super();
    }

    @Override
    public String GUID() {
        return "currency/fifty_tier";
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Large Dungeon Key Upgrade";
    }

    @Override
    public int increaseTierBy() {
        return 50;
    }

    @Override
    public Item craftItem() {
        return ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(SkillItemTier.TIER4);
    }
}


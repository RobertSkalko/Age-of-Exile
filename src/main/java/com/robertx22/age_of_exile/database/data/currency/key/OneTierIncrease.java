package com.robertx22.age_of_exile.database.data.currency.key;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.item.Item;

public class OneTierIncrease extends IncreaseDungeonKeyTier {

    public OneTierIncrease() {
        super();
    }

    @Override
    public String GUID() {
        return "currency/one_tier";
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Small Dungeon Key Upgrade";
    }

    @Override
    public int increaseTierBy() {
        return 1;
    }

    @Override
    public Item craftItem() {
        return ModRegistry.TIERED.SMELTED_ESSENCE.get(SkillItemTier.TIER0);
    }
}

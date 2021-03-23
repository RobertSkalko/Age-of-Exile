package com.robertx22.age_of_exile.player_skills.items;

import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;

public class SmeltedEssenceItem extends TieredItem {

    public SmeltedEssenceItem(SkillItemTier tier) {
        super(tier);
    }

    @Override
    public String locNameForLangFile() {
        return "Smelted " + tier.word + " Essence";
    }

    @Override
    public String GUID() {
        return "mat/smelted/" + tier.tier;
    }

}

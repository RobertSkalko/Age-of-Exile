package com.robertx22.age_of_exile.player_skills.items.mining;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;

public class MiningStoneItem extends TieredItem implements IGatheringMat {

    public MiningStoneItem(SkillItemTier tier) {
        super(tier, new Settings().group(CreativeTabs.Professions));
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Stone";
    }

    @Override
    public String GUID() {
        return "stone/" + tier.tier;
    }
}


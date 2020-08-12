package com.robertx22.age_of_exile.database.data.rarities.containers;

import com.robertx22.age_of_exile.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.age_of_exile.database.data.rarities.RarityTypeEnum;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.data.rarities.skill_gems.CommonGem;
import com.robertx22.age_of_exile.database.data.rarities.skill_gems.MagicalGem;
import com.robertx22.age_of_exile.database.data.rarities.skill_gems.RareGem;

public class SkillGemRarities extends BaseRaritiesContainer<SkillGemRarity> {

    public SkillGemRarities() {
        super();
        add(new CommonGem());
        add(new MagicalGem());
        add(new RareGem());
        this.onInit();
    }

    @Override
    public RarityTypeEnum getType() {
        return RarityTypeEnum.SKILL_GEM;
    }

}



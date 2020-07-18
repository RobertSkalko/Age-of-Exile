package com.robertx22.mine_and_slash.database.data.rarities.containers;

import com.robertx22.mine_and_slash.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.rarities.RarityTypeEnum;
import com.robertx22.mine_and_slash.database.data.rarities.SkillGemRarity;
import com.robertx22.mine_and_slash.database.data.rarities.mobs.EpicMob;
import com.robertx22.mine_and_slash.database.data.rarities.mobs.LegendaryMob;
import com.robertx22.mine_and_slash.database.data.rarities.mobs.RareMob;
import com.robertx22.mine_and_slash.database.data.rarities.skill_gems.CommonGem;
import com.robertx22.mine_and_slash.database.data.rarities.skill_gems.MagicalGem;
import com.robertx22.mine_and_slash.database.data.rarities.skill_gems.RareGem;

import java.util.Arrays;
import java.util.List;

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

    public List<MobRarity> getElite() {
        return Arrays.asList(LegendaryMob.getInstance(), EpicMob.getInstance(), RareMob.getInstance());
    }

}



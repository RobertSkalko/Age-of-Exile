package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.SkillGemRarity;
import com.robertx22.mine_and_slash.database.data.rarities.skill_gems.CommonGem;
import com.robertx22.mine_and_slash.datapacks.generators.RarityGenerator;

public class SkillGemRarityLoader extends BaseRarityDatapackLoader<SkillGemRarity> {

    public static String ID = "rarity/skill_gem";

    public SkillGemRarityLoader() {
        super(Rarities.SkillGems, ID, x -> new CommonGem()
            .fromJson(x));
    }

    @Override
    public RarityGenerator getDatapackGenerator() {
        return new RarityGenerator(Rarities.SkillGems.getSeriazables(), ID);
    }

}


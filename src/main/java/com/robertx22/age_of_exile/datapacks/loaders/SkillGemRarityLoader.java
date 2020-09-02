package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.rarities.ISkillGemRarity;
import com.robertx22.age_of_exile.database.data.rarities.containers.SkillGemRarities;
import com.robertx22.age_of_exile.datapacks.generators.RarityGenerator;

public class SkillGemRarityLoader extends BaseRarityDatapackLoader<ISkillGemRarity> {

    public static String ID = "rarity/skill_gem";

    public SkillGemRarityLoader() {
        super(Rarities.SkillGems, ID, x -> new SkillGemRarities.CommonGem()
            .fromJson(x));
    }

    @Override
    public RarityGenerator getDatapackGenerator() {
        return new RarityGenerator(Rarities.SkillGems.getSeriazables(), ID);
    }

}


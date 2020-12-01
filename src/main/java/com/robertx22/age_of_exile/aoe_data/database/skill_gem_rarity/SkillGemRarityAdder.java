package com.robertx22.age_of_exile.aoe_data.database.skill_gem_rarity;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SkillGemRarityAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SkillGemRarity common = new SkillGemRarity();
        common.weight = 10000;
        common.setCommonFields();
        common.stat_percents = new MinMax(10, 25);
        common.addToSerializables();

        SkillGemRarity rare = new SkillGemRarity();
        rare.weight = 7500;
        rare.setRareFields();
        rare.stat_percents = new MinMax(25, 40);
        rare.addToSerializables();

        SkillGemRarity epic = new SkillGemRarity();
        epic.weight = 1000;
        epic.setEpicFields();
        epic.stat_percents = new MinMax(40, 75);
        epic.addToSerializables();

        SkillGemRarity mythic = new SkillGemRarity();
        mythic.weight = 100;
        mythic.setMythicFields();
        mythic.stat_percents = new MinMax(75, 100);
        mythic.addToSerializables();

    }
}

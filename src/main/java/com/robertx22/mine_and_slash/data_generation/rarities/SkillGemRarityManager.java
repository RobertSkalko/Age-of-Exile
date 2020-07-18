package com.robertx22.mine_and_slash.data_generation.rarities;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.SkillGemRarity;
import com.robertx22.mine_and_slash.database.data.rarities.skill_gems.CommonGem;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.RarityProvider;
import net.minecraft.data.DataGenerator;

public class SkillGemRarityManager extends BaseRarityDatapackManager<SkillGemRarity> {

    public static String ID = "rarity/skill_gem";

    public SkillGemRarityManager() {
        super(Rarities.SkillGems, ID, x -> new CommonGem()
            .fromJson(x));
    }

    @Override
    public RarityProvider getProvider(DataGenerator gen) {
        return new RarityProvider(gen, Rarities.SkillGems.getAllRarities(), ID);
    }

}


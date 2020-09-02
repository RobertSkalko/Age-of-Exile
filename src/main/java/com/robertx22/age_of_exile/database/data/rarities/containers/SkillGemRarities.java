package com.robertx22.age_of_exile.database.data.rarities.containers;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.age_of_exile.database.data.rarities.ISkillGemRarity;
import com.robertx22.age_of_exile.database.data.rarities.RarityTypeEnum;
import com.robertx22.age_of_exile.database.data.rarities.serialization.SkillGemRarity;

public class SkillGemRarities extends BaseRaritiesContainer<ISkillGemRarity> {

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

    public static class CommonGem extends SkillGemRarity {
        public CommonGem() {
            this.setCommonFields();
            this.weight = 1000;
            this.stat_percents = new MinMax(20, 75);
        }
    }

    public class MagicalGem extends SkillGemRarity {
        public MagicalGem() {
            this.setMagicalFields();
            this.weight = 500;
            this.stat_percents = new MinMax(20, 75);
        }
    }

    public class RareGem extends SkillGemRarity {
        public RareGem() {
            this.setRareFields();
            this.weight = 200;
            this.stat_percents = new MinMax(50, 100);
        }
    }

}



package com.robertx22.age_of_exile.database.data.rarities.serialization;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.ISkillGemRarity;

public class SkillGemRarity extends BaseRarity implements ISkillGemRarity, IAutoGson<SkillGemRarity> {

    public MinMax stat_percents = new MinMax(0, 100);

    @Override
    public MinMax statPercents() {
        return this.stat_percents;
    }

    @Override
    public Class<SkillGemRarity> getClassForSerialization() {
        return SkillGemRarity.class;
    }
}

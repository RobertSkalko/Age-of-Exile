package com.robertx22.mine_and_slash.database.data.rarities.serialization;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.rarities.SkillGemRarity;

public class SerializedSkillGemRarity extends SerializedBaseRarity implements SkillGemRarity {

    public SerializedSkillGemRarity(SerializedBaseRarity baser) {
        super(baser);
    }

    public MinMax statPerc;

    @Override
    public MinMax statPercents() {
        return statPerc;
    }
}

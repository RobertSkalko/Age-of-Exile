package com.robertx22.age_of_exile.database.data.rarities.skill_gems;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import net.minecraft.util.Formatting;

public class CommonGem implements SkillGemRarity {
    @Override
    public MinMax statPercents() {
        return new MinMax(20, 75);
    }

    @Override
    public String GUID() {
        return "common";
    }

    @Override
    public int Rank() {
        return 0;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public Formatting textFormatting() {
        return Formatting.GRAY;
    }

    @Override
    public String locNameForLangFile() {
        return "Common";
    }
}

package com.robertx22.age_of_exile.database.data.rarities.skill_gems;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import net.minecraft.util.Formatting;

public class RareGem implements SkillGemRarity {
    @Override
    public MinMax statPercents() {
        return new MinMax(50, 100);
    }

    @Override
    public String GUID() {
        return "rare";
    }

    @Override
    public int Rank() {
        return 2;
    }

    @Override
    public int Weight() {
        return 250;
    }

    @Override
    public Formatting textFormatting() {
        return Formatting.YELLOW;
    }

    @Override
    public String locNameForLangFile() {
        return "Rare";
    }
}

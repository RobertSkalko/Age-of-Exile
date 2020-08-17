package com.robertx22.age_of_exile.database.data.unique_items.jewelry.necklace;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ImmuneToEffectStat;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaBurnResistance;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class AngelicProtectionNecklace implements IUnique {
    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(50, 100, ManaBurnResistance.getInstance(), ModType.FLAT),
            new StatModifier(1, 1, ImmuneToEffectStat.POISON, ModType.FLAT),
            new StatModifier(3, 5, PlusResourceOnKill.MANA, ModType.FLAT),
            new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "If your guardian is careful enough, you just might think they don't exist.";
    }

    @Override
    public String locNameForLangFile() {
        return "Angelic Protection";
    }

    @Override
    public String GUID() {
        return "angel_prot_necklace";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.START_TO_LOW_ALL_RES_NECKLACE;
    }
}



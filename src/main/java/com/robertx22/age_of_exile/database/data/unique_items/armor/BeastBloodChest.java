package com.robertx22.age_of_exile.database.data.unique_items.armor;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ImmuneToEffectStat;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class BeastBloodChest implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(5, 15, Health.getInstance(), ModType.FLAT),
            new StatModifier(1, 1, RegeneratePercentStat.HEALTH, ModType.FLAT),
            new StatModifier(1, 1, ImmuneToEffectStat.HUNGER, ModType.FLAT),
            new StatModifier(-15, -5, IncreasedItemQuantity.getInstance(), ModType.FLAT),
            new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "Accept the blood, suffer the Sacrifice. Be reborn anew.";
    }

    @Override
    public String locNameForLangFile() {
        return "Blood of the Beast";
    }

    @Override
    public String GUID() {
        return "beast_blood";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.END_PLATE_CHEST;
    }
}

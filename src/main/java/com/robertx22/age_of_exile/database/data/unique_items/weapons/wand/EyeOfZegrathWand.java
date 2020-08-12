package com.robertx22.age_of_exile.database.data.unique_items.weapons.wand;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class EyeOfZegrathWand implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(10, 30, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE),
            new StatModifier(5, 10, ChanceToApplyEffect.BURN, ModType.FLAT),
            new StatModifier(5, 10, ChanceToApplyEffect.CHILL, ModType.FLAT),
            new StatModifier(5, 10, ChanceToApplyEffect.POISON, ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "A cultist once so powerful, even his remnant eye inflicts ills on others.";
    }

    @Override
    public String locNameForLangFile() {
        return "Eye of Zegrath";
    }

    @Override
    public String GUID() {
        return "eye_of_zegrath";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.MID_WAND;
    }
}

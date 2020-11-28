package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase.SpecificWeaponElementalDamageEffect;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

import java.util.ArrayList;
import java.util.List;

public class SpecificElementalWeaponDamage extends Stat implements IStatEffects, IGenerated<SpecificElementalWeaponDamage> {

    @Override
    public List<SpecificElementalWeaponDamage> generateAllPossibleStatVariations() {
        List<SpecificElementalWeaponDamage> list = new ArrayList<>();
        for (WeaponTypes x : WeaponTypes.getAll()) {
            list.add(new SpecificElementalWeaponDamage(x));
        }
        return list;
    }

    private WeaponTypes weaponType;

    public SpecificElementalWeaponDamage(WeaponTypes type) {
        this.weaponType = type;
        this.statGroup = StatGroup.WEAPON;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases elemental damage of x weapon";
    }

    public WeaponTypes weaponType() {
        return this.weaponType;
    }

    @Override
    public String GUID() {
        return "ele_" + this.weaponType.id + "_damage";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public IStatEffect getEffect() {
        return SpecificWeaponElementalDamageEffect.INSTANCE;
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "ele_wep_damage";
    }

    @Override
    public String locNameForLangFile() {
        return "Elemental " + this.weaponType()
            .name() + " Damage";
    }

}

package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.AttackDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

import java.util.ArrayList;
import java.util.List;

public class AttackDamage extends ElementalStat implements IStatEffects, ILocalStat {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {

        List<Stat> list = new ArrayList<>();
        Elements.getAllSingleIncludingPhysical()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;
    }

    public AttackDamage(Elements element) {
        super(element);
        this.uses_second_val = true;
        this.scaling = StatScaling.NORMAL;

        this.statGroup = StatGroup.ELEMENTAL;
    }

    @Override
    public boolean IsNativeToGearType(BaseGearType slot) {
        return slot.isWeapon();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new AttackDamage(element);
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public IStatEffect getEffect() {
        return new AttackDamageEffect();
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "ele_atk_dmg";
    }

    @Override
    public String locNameForLangFile() {
        if (element.equals(Elements.Elemental)) {
            return getElement().name() + "Attack Damage";
        } else {
            return getElement().dmgName + " Damage";
        }
    }

    @Override
    public String locDescForLangFile() {
        return "Adds x element damage on weapon hit";
    }

    @Override
    public String GUID() {
        return this.getElement().guidName + "_weapon_damage";
    }

}

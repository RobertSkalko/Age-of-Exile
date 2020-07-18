package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.stats.ILocalStat;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.ElementalAttackDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.types.ElementalStat;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

import java.util.List;

public class WeaponDamage extends ElementalStat implements IStatEffects, ILocalStat {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.add(newGeneratedInstance(Elements.Physical));
        return list;
    }

    @Override
    public StatScaling getScaling() {
        return StatScaling.SCALING;
    }

    @Override
    public Stat.StatGroup statGroup() {
        return Stat.StatGroup.EleAttackDamage;
    }

    @Override
    public boolean UsesSecondValue() {
        return true;
    }

    public WeaponDamage(Elements element) {
        super(element);
    }

    @Override
    public boolean IsNativeToGearType(BaseGearType slot) {
        return slot.isWeapon();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new WeaponDamage(element);
    }

    @Override
    public String getIconPath() {
        return "ele_atk_dmg/" + element.guidName;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public IStatEffect getEffect() {
        return new ElementalAttackDamageEffect();
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

package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.ArrayList;
import java.util.List;

public class AttackDamage extends ElementalStat {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {

        List<Stat> list = new ArrayList<>();
        Elements.getAllSingleIncludingPhysical()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;
    }

    public AttackDamage(Elements element) {
        super(element);
        this.use_sec_val = true;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.ELEMENTAL;
        this.statEffect = new Effect();

        this.format = element.format.getName();
        this.icon = element.icon;
        this.isLocalTo = x -> x.isWeapon();
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

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.Second.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            effect.addBonusEleDmg(stat.getElement(), data.getRandomRangeValue());
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return stat.getElement() != Elements.Physical && effect.data.isBasicAttack();
        }

    }

}

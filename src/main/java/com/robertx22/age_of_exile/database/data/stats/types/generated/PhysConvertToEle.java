package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;

import java.util.List;

public class PhysConvertToEle extends ElementalStat {

    public static MapWrapper<Elements, PhysConvertToEle> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllSingleVariations();
        list.forEach(x -> MAP.put(x.getElement(), (PhysConvertToEle) x));
        return list;

    }

    public PhysConvertToEle(Elements element) {
        super(element);
        this.scaling = StatScaling.NONE;

        this.statEffect = new Effect();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new PhysConvertToEle(element);
    }

    @Override
    public String GUID() {
        return "convert_" + this.getElement().guidName + "_to_phys";
    }

    @Override
    public String locDescForLangFile() {
        return "Turns % of phys atk dmg into ele";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "turn_phys_to_ele";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.AlmostLast.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            float dmg = effect.data.getNumber() * data.getAverageValue() / 100F;
            effect.addBonusEleDmg(stat.getElement(), dmg);
            effect.data.getNumber(EventData.NUMBER).number -= dmg;

            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.getAttackType()
                .equals(AttackType.ATTACK);
        }

    }

    @Override
    public String locNameForLangFile() {
        return "Physical to " + this.getElement()
            .dmgName + " Damage";
    }

}



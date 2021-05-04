package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalPenetration extends ElementalStat {
    public static MapWrapper<Elements, ElementalPenetration> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalPenetration) x));
        return list;

    }

    public ElementalPenetration(Elements element) {
        super(element);
        this.min = 0;
        this.group = StatGroup.ELEMENTAL;

        this.statEffect = new Effect();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {

        return new ElementalPenetration(element);
    }

    @Override
    public String GUID() {
        return this.getElement().guidName + "_penetration";
    }

    @Override
    public String locDescForLangFile() {
        return "Ignores that much resists, it works as subtraction.";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "ele_pene";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement()
            .dmgName + " Penetration";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.First.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            effect.data.getNumber(EventData.PENETRATION).number += data.getAverageValue();
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.GetElement()
                .equals(stat.getElement()) && !stat.getElement()
                .equals(Elements.Elemental);
        }

    }

}

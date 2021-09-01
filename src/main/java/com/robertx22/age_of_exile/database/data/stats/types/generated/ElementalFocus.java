package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.SingleElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.List;

public class ElementalFocus extends SingleElementalStat {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        return list;

    }

    public ElementalFocus(Elements element) {
        super(element);
        this.statEffect = new Effect();
    }

    @Override
    public String locNameForLangFile() {
        return element.name() + " Focus";
    }

    @Override
    public String GUID() {
        return element.guidName + "_focus";
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new ElementalFocus(element);
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return element;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases Dmg for that element by a percent but decreases dmg from all other elements.";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "elemental_focus";
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

            if (effect.getElement()
                .equals(stat.getElement())) {
                effect.increaseByPercent(data.getValue());
            } else {
                effect.increaseByPercent(-data.getValue());
            }

            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.getElement() != null && effect.getElement() != Elements.Physical;
        }

    }
}

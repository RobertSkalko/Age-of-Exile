package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ArmorPenetration extends Stat {

    public static ArmorPenetration getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Ignores x armor";
    }

    public static String GUID = "armor_penetration";

    private ArmorPenetration() {
        this.min = 0;
        this.statEffect = new Effect();
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Armor Penetration";
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
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            effect.SetArmorPenetration(effect.GetArmorPenetration() + (int) data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.GetElement() == Elements.Physical;
        }

    }

    private static class SingletonHolder {
        private static final ArmorPenetration INSTANCE = new ArmorPenetration();
    }
}


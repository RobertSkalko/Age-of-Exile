package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResource;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IncreasedLeech extends Stat {
    public static String GUID = "inc_leech";

    private IncreasedLeech() {
        this.group = StatGroup.RESTORATION;
        this.scaling = StatScaling.SLOW;
        this.statEffect = new Effect();
    }

    public static IncreasedLeech getInstance() {
        return IncreasedLeech.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects all resource leech stats like: onhit, leech dmg, on kill restore etc";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Increased Leech Effects";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.Last.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            float multi = data.getMultiplier();
            effect.toRestore.forEach(x -> {
                if (x.restoreType == RestoreResource.RestoreType.LEECH) {
                    x.amount *= multi;
                }
            });
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return !effect.toRestore.isEmpty();
        }

    }

    private static class SingletonHolder {
        private static final IncreasedLeech INSTANCE = new IncreasedLeech();
    }
}

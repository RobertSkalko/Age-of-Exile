package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.SkillEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.utils.RandomUtils;

public class TripleDropChance extends Stat {

    public static String GUID = "triple_drop_chance";

    public static TripleDropChance getInstance() {
        return TripleDropChance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    private TripleDropChance() {
        this.base = 0;
        this.min = 0;
        this.scaling = StatScaling.SLOW;
        this.group = StatGroup.Misc;

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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Triple Drop Chance";
    }

    private static class SingletonHolder {
        private static final TripleDropChance INSTANCE = new TripleDropChance();
    }

    private class Effect extends SkillEffect {

        @Override
        public SkillDropEvent activate(SkillDropEvent effect, StatData data, Stat stat) {

            effect.originalDrops.forEach(x -> {
                effect.extraDrops.add(x.copy());
                effect.extraDrops.add(x.copy());
            });
            return effect;
        }

        @Override
        public boolean canActivate(SkillDropEvent effect, StatData data, Stat stat) {
            return RandomUtils.roll(data.getAverageValue());
        }

        @Override
        public int GetPriority() {
            return 0;
        }
    }
}


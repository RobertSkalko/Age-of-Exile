package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.SkillEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class DoubleDropChance extends Stat {

    public static String GUID = "double_drop_chance";

    public static DoubleDropChance getInstance() {
        return DoubleDropChance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    private DoubleDropChance() {
        this.base_val = 0;
        this.min_val = 0;
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.Misc;

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
        return "Double Drop Chance";
    }

    private static class SingletonHolder {
        private static final DoubleDropChance INSTANCE = new DoubleDropChance();
    }

    private class Effect extends SkillEffect {

        @Override
        public SkillDropData activate(SkillDropData effect, StatData data, Stat stat) {

            effect.originalDrops.forEach(x -> {
                effect.extraDrops.add(x.copy());
            });
            return effect;
        }

        @Override
        public boolean canActivate(SkillDropData effect, StatData data, Stat stat) {
            return RandomUtils.roll(data.getAverageValue());
        }

        @Override
        public int GetPriority() {
            return 0;
        }
    }
}

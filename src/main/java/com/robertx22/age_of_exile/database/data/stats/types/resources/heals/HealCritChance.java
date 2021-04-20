package com.robertx22.age_of_exile.database.data.stats.types.resources.heals;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellHealEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.util.Formatting;

public class HealCritChance extends Stat {

    public static String GUID = "crit_heal_chance";

    public static HealCritChance getInstance() {
        return HealCritChance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance of critical";
    }

    private HealCritChance() {
        this.base_val = 0;
        this.min_val = 0;
        this.max_val = 100;
        this.statGroup = StatGroup.MAIN;

        this.textIcon = "\u2694";
        this.textFormat = Formatting.YELLOW;

        this.statEffect = new BaseHealEffect() {
            @Override
            public HealEffect activate(HealEffect effect, StatData data, Stat stat) {
                effect.increaseByPercent(data.getAverageValue());
                return effect;
            }

            @Override
            public boolean canActivate(HealEffect effect, StatData data, Stat stat) {
                return effect instanceof SpellHealEffect && RandomUtils.roll(data.getAverageValue());
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return Priority.Second.priority;
            }
        };
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Crit Heal Chance";
    }

    private static class SingletonHolder {
        private static final HealCritChance INSTANCE = new HealCritChance();
    }
}



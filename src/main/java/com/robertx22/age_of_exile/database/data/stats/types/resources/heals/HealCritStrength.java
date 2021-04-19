package com.robertx22.age_of_exile.database.data.stats.types.resources.heals;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellHealEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class HealCritStrength extends Stat {

    public static String GUID = "heal_crit_dmg";

    public static HealCritStrength getInstance() {
        return HealCritStrength.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "If Critical, multiply by x";
    }

    private HealCritStrength() {
        this.base_val = 50;
        this.min_val = 0;
        this.max_val = 500;
        this.statGroup = StatGroup.MAIN;

        this.textIcon = "\u2694";
        this.textFormat = Formatting.GOLD;

        this.statEffect = new BaseHealEffect() {
            @Override
            public HealEffect activate(HealEffect effect, StatData data, Stat stat) {
                effect.increaseByPercent(data.getAverageValue());
                return effect;
            }

            @Override
            public boolean canActivate(HealEffect effect, StatData data, Stat stat) {
                return effect instanceof SpellHealEffect && effect.isCrit;
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return Priority.Last.priority;
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
        return "Crit Heal Damage";
    }

    private static class SingletonHolder {
        private static final HealCritStrength INSTANCE = new HealCritStrength();
    }
}


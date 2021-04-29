package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellHealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class HealPower extends Stat {
    public static String GUID = "increase_healing";

    private HealPower() {
        this.statGroup = StatGroup.RESTORATION;
        this.scaling = StatScaling.SLOW;
        this.statEffect = new Effect();

        this.textFormat = Formatting.YELLOW;
    }

    public static HealPower getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases spell related heals.";
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
        return "Heal Strength";
    }

    private static class Effect extends BaseHealEffect {

        @Override
        public int GetPriority() {
            return Priority.First.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public HealEffect activate(HealEffect effect, StatData data, Stat stat) {
            effect.data.getNumber(EventData.NUMBER).number *= data.getMultiplier();
            return effect;
        }

        @Override
        public boolean canActivate(HealEffect effect, StatData data, Stat stat) {
            return effect instanceof SpellHealEffect;
        }

    }

    private static class SingletonHolder {
        private static final HealPower INSTANCE = new HealPower();
    }
}


package com.robertx22.age_of_exile.database.data.stats.types.shields;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseShieldEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageAbsorbEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class ShieldDuration extends Stat {

    public static String GUID = "shield_duration";

    private ShieldDuration() {
        this.scaling = StatScaling.NONE;
        this.statEffect = new ShieldDuration.Effect();

        this.textIcon = "\u2748";
        this.textFormat = Formatting.GREEN;
    }

    public static ShieldDuration getInstance() {
        return ShieldDuration.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Boosts the duration of your shields.";
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
        return "Shield Duration";
    }

    private static class Effect extends BaseShieldEffect {
        @Override
        public DamageAbsorbEffect activate(DamageAbsorbEffect effect, StatData data, Stat stat) {
            effect.seconds *= data.getMultiplier();
            return effect;
        }

        @Override
        public boolean canActivate(DamageAbsorbEffect effect, StatData data, Stat stat) {
            return true;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public int GetPriority() {
            return 0;
        }
    }

    private static class SingletonHolder {
        private static final ShieldDuration INSTANCE = new ShieldDuration();
    }
}



package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class DodgeRating extends Stat implements IUsableStat {

    public static String GUID = "dodge";

    public static DodgeRating getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to ignore attack damage";
    }

    private DodgeRating() {
        this.min_val = 0;
        this.scaling = StatScaling.NORMAL;
        this.statGroup = StatGroup.MAIN;

        this.statEffect = new Effect();

        this.textIcon = "\u2748";
        this.textFormat = Formatting.DARK_GREEN;

        this.isLocalTo = x -> x.isArmor();
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
        return "Dodge Rating";
    }

    @Override
    public float getMaxMulti() {
        return 0.8F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 15;
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.afterThis(Accuracy.getInstance().statEffect
                .GetPriority());
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            DodgeRating dodge = (DodgeRating) stat;

            float totalDodge = MathHelper.clamp(data.getAverageValue() - effect.attackerAccuracy, 0, Integer.MAX_VALUE);

            float chance = dodge.getUsableValue((int) totalDodge, effect.sourceData.getLevel()) * 100;

            if (RandomUtils.roll(chance)) {
                effect.data.getNumber(EventData.NUMBER).number = 0;
                effect.isDodged = true;
            } else {
                if (RandomUtils.roll(chance)) {
                    effect.accuracyCritRollFailed = true;
                }
            }

            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.attackType.isAttack();
        }
    }

    private static class SingletonHolder {
        private static final DodgeRating INSTANCE = new DodgeRating();
    }
}

package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChanceToApplyEffect extends Stat {

    public static ChanceToApplyEffect BURN = new ChanceToApplyEffect(Elements.Fire, NegativeEffects.BURN, "Burn", "burn");
    public static ChanceToApplyEffect FROSTBURN = new ChanceToApplyEffect(Elements.Water, NegativeEffects.FROSTBURN, "Frostburn", "frostburn");
    public static ChanceToApplyEffect POISON = new ChanceToApplyEffect(Elements.Nature, NegativeEffects.POISON, "Poison", "poison");
    public static ChanceToApplyEffect SHOCK = new ChanceToApplyEffect(Elements.Thunder, NegativeEffects.SHOCK, "Shock", "shock");
    public static ChanceToApplyEffect BLEED = new ChanceToApplyEffect(Elements.Physical, NegativeEffects.BLEED, "Bleed", "bleed");

    String effect;
    String locname;
    String id;
    Elements ele;

    public ChanceToApplyEffect(Elements ele, String effect, String locname, String id) {
        this.effect = effect;
        this.locname = locname;
        this.id = id;
        this.ele = ele;

        this.add$plusminus$toTooltip = false;
        this.min_val = 0;

        this.statEffect = new Effect(effect, ele, AttackType.SPELL, AttackType.ATTACK);
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to apply effect on hit, only works on appropriate element. (Burn = Fire damage) ";
    }

    @Override
    public String locNameForLangFile() {
        return "To Apply " + locname;
    }

    @Override
    public String GUID() {
        return "chance_of_" + id;
    }

    static class Effect extends BaseStatEffect<DamageEffect> {

        String statusEffect;
        Set<AttackType> onEffectType;
        Elements ele;

        public Effect(String effect, Elements ele, AttackType... onEffectType) {
            super(DamageEffect.class);
            this.statusEffect = effect;
            this.onEffectType = new HashSet<>(Arrays.asList(onEffectType));
            this.ele = ele;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public int GetPriority() {
            return Priority.First.priority;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            ExileEffectsManager.apply(effect.sourceData.getLevel(), Database.ExileEffects()
                .get(this.statusEffect), effect.source, effect.target, 20 * 10);

            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            if (RandomUtils.roll(data.getAverageValue())) {
                return onEffectType.contains(effect.getAttackType()) && effect.element == ele;
            }
            return false;
        }
    }

}

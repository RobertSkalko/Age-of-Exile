package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class ChanceToApplyEffect extends Stat {

    public static ChanceToApplyEffect BURN = new ChanceToApplyEffect(Elements.Fire, NegativeEffects.BURN, "Burn", "burn");
    public static ChanceToApplyEffect FROSTBURN = new ChanceToApplyEffect(Elements.Water, NegativeEffects.FROSTBURN, "Frostburn", "frostburn");
    public static ChanceToApplyEffect POISON = new ChanceToApplyEffect(Elements.Nature, NegativeEffects.POISON, "Poison", "poison");
    public static ChanceToApplyEffect BLEED = new ChanceToApplyEffect(Elements.Physical, NegativeEffects.BLEED, "Bleed", "bleed");
    public static ChanceToApplyEffect TORMENT = new ChanceToApplyEffect(Elements.Dark, NegativeEffects.TORMENT, "Torment", "torment");

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

        this.isLongStat = true;

        this.statEffect = new Effect(effect, ele);
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
        return SpecialStats.format(
            "Your " + this.ele.getIconNameFormat() + " Attacks have " + SpecialStats.VAL1 + "% chance of applying " + locname
        );
    }

    @Override
    public String GUID() {
        return "chance_of_" + id;
    }

    static class Effect extends BaseStatEffect<DamageEffect> {

        String statusEffect;
        Elements ele;

        public Effect(String effect, Elements ele) {
            super(DamageEffect.class);
            this.statusEffect = effect;
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
                .get(this.statusEffect), effect.source, effect.target, 20 * 6);

            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            if (effect.element.elementsMatch(ele)) {
                if (RandomUtils.roll(data.getAverageValue())) {
                    return effect.getAttackType()
                        .isAttack() || effect.getAttackType()
                        .isSpell();
                }
            }
            return false;
        }
    }

}

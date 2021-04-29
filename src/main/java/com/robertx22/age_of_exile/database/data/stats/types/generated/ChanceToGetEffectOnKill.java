package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.MobKillByDamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class ChanceToGetEffectOnKill extends Stat {

    public static ChanceToGetEffectOnKill BLOODLUST = new ChanceToGetEffectOnKill(Elements.Physical, BeneficialEffects.BLOODLUST, "Bloodlust", "bloodlust");

    String effect;
    String locname;
    String id;
    Elements ele;

    public ChanceToGetEffectOnKill(Elements ele, String effect, String locname, String id) {
        this.effect = effect;
        this.locname = locname;
        this.id = id;
        this.ele = ele;

        this.scaling = StatScaling.NONE;

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
            "Your " + this.ele.getIconNameFormat() + " Killing blows have " + SpecialStats.VAL1 + "% chance of giving you " + locname
        );
    }

    @Override
    public String GUID() {
        return "chance_of_" + id;
    }

    static class Effect extends BaseStatEffect<MobKillByDamageEvent> {

        String statusEffect;
        Elements ele;

        public Effect(String effect, Elements ele) {
            super(MobKillByDamageEvent.class);
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
        public MobKillByDamageEvent activate(MobKillByDamageEvent effect, StatData data, Stat stat) {
            ExileEffectsManager.apply(effect.sourceData.getLevel(), Database.ExileEffects()
                .get(this.statusEffect), effect.source, effect.source, 20 * 10);
            return effect;
        }

        @Override
        public boolean canActivate(MobKillByDamageEvent effect, StatData data, Stat stat) {
            if (effect.damageEvent.element.elementsMatch(ele)) {
                if (RandomUtils.roll(data.getAverageValue())) {
                    return effect.damageEvent.getAttackType()
                        .isAttack() || effect.damageEvent.getAttackType()
                        .isSpell();
                }
            }
            return false;
        }
    }

}


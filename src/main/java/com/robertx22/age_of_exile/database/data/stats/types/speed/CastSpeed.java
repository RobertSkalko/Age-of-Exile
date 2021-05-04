package com.robertx22.age_of_exile.database.data.stats.types.speed;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class CastSpeed extends Stat {

    private CastSpeed() {
        this.max = 75;
        this.statEffect = new Effect();
    }

    public static CastSpeed getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects amount of time needed to cast spells. If the spell is instant, it reduces the cooldown";
    }

    @Override
    public String locNameForLangFile() {
        return "Cast Speed";
    }

    @Override
    public String GUID() {
        return "cast_speed";
    }

    static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalculationEvent activate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            effect.spellConfig.add(SpellModEnum.CAST_SPEED, -data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            Spell spell = effect.getSpell();
            return spell.config.style.getAttackType() == AttackType.spell;
        }

    }

    private static class SingletonHolder {
        private static final CastSpeed INSTANCE = new CastSpeed();
    }
}


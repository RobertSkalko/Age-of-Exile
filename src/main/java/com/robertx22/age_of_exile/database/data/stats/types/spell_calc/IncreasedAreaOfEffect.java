package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class IncreasedAreaOfEffect extends Stat {

    private IncreasedAreaOfEffect() {
        this.max = 500;

        this.statEffect = new Effect();
    }

    public static IncreasedAreaOfEffect getInstance() {
        return IncreasedAreaOfEffect.SingletonHolder.INSTANCE;
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
        return "Spell aoe effects will be larger.";
    }

    @Override
    public String locNameForLangFile() {
        return "Area of Effect";
    }

    @Override
    public String GUID() {
        return "inc_aoe";
    }

    static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalculationEvent activate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            effect.spellConfig.add(SpellModEnum.AREA, data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            return effect.getSpell()
                .is(SkillGemTag.area);
        }

    }

    private static class SingletonHolder {
        private static final IncreasedAreaOfEffect INSTANCE = new IncreasedAreaOfEffect();
    }
}

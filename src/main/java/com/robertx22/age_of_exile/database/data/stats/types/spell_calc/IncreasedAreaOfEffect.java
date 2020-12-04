package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class IncreasedAreaOfEffect extends Stat {

    private IncreasedAreaOfEffect() {
        this.max_val = 500;

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
        return "Increased Area of Effect";
    }

    @Override
    public String GUID() {
        return "inc_aoe";
    }

    static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            effect.data.add(SpellModEnum.AREA, data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            return effect.getSpell()
                .is(SkillGemTag.AREA);
        }

    }

    private static class SingletonHolder {
        private static final IncreasedAreaOfEffect INSTANCE = new IncreasedAreaOfEffect();
    }
}

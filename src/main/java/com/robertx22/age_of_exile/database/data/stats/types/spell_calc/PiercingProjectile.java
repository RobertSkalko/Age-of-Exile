package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class PiercingProjectile extends Stat {

    private PiercingProjectile() {
        this.max = 1;
        this.statEffect = new Effect();
    }

    public static PiercingProjectile getInstance() {
        return PiercingProjectile.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Makes spell pierce enemies and keep on";
    }

    @Override
    public String locNameForLangFile() {
        return "Piercing Projectiles";
    }

    @Override
    public String GUID() {
        return "piercing_projectiles";
    }

    static class Effect extends BaseSpellCalcEffect {
        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            effect.data.piercing = data.getAverageValue() > 0;
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            return effect.getSpell()
                .is(SkillGemTag.PROJECTILE);
        }
    }

    private static class SingletonHolder {
        private static final PiercingProjectile INSTANCE = new PiercingProjectile();
    }
}


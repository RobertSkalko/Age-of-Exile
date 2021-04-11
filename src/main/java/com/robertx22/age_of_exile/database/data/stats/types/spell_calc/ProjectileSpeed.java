package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class ProjectileSpeed extends Stat {

    private ProjectileSpeed() {
        this.max_val = 200;

        this.statEffect = new Effect();

        this.textIcon = "\u27B9";
        this.textFormat = Formatting.GREEN;
    }

    public static ProjectileSpeed getInstance() {
        return ProjectileSpeed.SingletonHolder.INSTANCE;
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
        return "Makes spell projectiles faster";
    }

    @Override
    public String locNameForLangFile() {
        return "Faster Projectiles";
    }

    @Override
    public String GUID() {
        return "faster_projectiles";
    }

    static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            effect.data.add(SpellModEnum.PROJECTILE_SPEED, data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            return effect.getSpell()
                .is(SkillGemTag.PROJECTILE);
        }

    }

    private static class SingletonHolder {
        private static final ProjectileSpeed INSTANCE = new ProjectileSpeed();
    }
}


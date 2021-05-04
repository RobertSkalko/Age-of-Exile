package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class ProjectileSpeed extends Stat {

    private ProjectileSpeed() {
        this.max = 200;

        this.statEffect = new Effect();

        this.icon = "\u27B9";
        this.format = Formatting.GREEN;
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
        public SpellStatsCalculationEvent activate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            effect.spellConfig.add(SpellModEnum.PROJECTILE_SPEED, data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            return effect.getSpell()
                .is(SpellTag.projectile);
        }

    }

    private static class SingletonHolder {
        private static final ProjectileSpeed INSTANCE = new ProjectileSpeed();
    }
}


package com.robertx22.age_of_exile.database.data.stats.types.speed;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class AttackSpeed extends Stat {

    public static String GUID = "attack_speed";

    private AttackSpeed() {
        this.base = 100;
        this.statEffect = new Effect();
    }

    public static AttackSpeed getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Lowers cooldown of melee attacks.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Attack Speed";
    }

    static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalculationEvent activate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            effect.spellConfig.add(SpellModEnum.CAST_SPEED, -(data.getAverageValue() - stat.base));
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
            Spell spell = effect.getSpell();
            return spell.config.style.getAttackType() == AttackType.attack;
        }

    }

    private static class SingletonHolder {
        private static final AttackSpeed INSTANCE = new AttackSpeed();
    }
}

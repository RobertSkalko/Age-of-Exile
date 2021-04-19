package com.robertx22.age_of_exile.database.data.stats.types.speed;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class AttackSpeed extends Stat {

    public static String GUID = "attack_speed";

    private AttackSpeed() {
        this.base_val = 100;
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
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            effect.data.add(SpellModEnum.CAST_SPEED, -(data.getAverageValue() - stat.base_val));
            return effect;
        }

        @Override
        public boolean canActivate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            Spell spell = effect.getSpell();
            return spell.config.style.getAttackType() == AttackType.ATTACK;
        }

    }

    private static class SingletonHolder {
        private static final AttackSpeed INSTANCE = new AttackSpeed();
    }
}

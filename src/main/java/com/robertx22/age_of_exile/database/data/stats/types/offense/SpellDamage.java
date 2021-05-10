package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.util.Formatting;

public class SpellDamage extends Stat {

    private SpellDamage() {
        this.scaling = StatScaling.SLOW;
        this.group = StatGroup.MAIN;

        this.statEffect = new Effect();
        this.format = Formatting.LIGHT_PURPLE.getName();
    }

    public static String GUID = "spell_damage";

    public static SpellDamage getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases DMG of all spells no matter the element";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Spell Damage";
    }

    private static class Effect extends BaseDamageEffect {

        private Effect() {
            super();
        }

        @Override
        public int GetPriority() {
            return Priority.Second.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            effect.increaseByPercent(data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.getAttackType()
                .isSpell() && effect.isSpell();
        }
    }

    private static class SingletonHolder {
        private static final SpellDamage INSTANCE = new SpellDamage();
    }
}

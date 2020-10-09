package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.AllSpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class SpellDamage extends Stat implements IStatEffects {

    private SpellDamage() {
        this.scaling = StatScaling.SLOW_SCALING;
        this.statGroup = StatGroup.MAIN;
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
    public IStatEffect getEffect() {
        return AllSpellDamageEffect.INSTANCE;
    }

    @Override
    public String locNameForLangFile() {
        return "Spell Damage";
    }

    private static class SingletonHolder {
        private static final SpellDamage INSTANCE = new SpellDamage();
    }
}

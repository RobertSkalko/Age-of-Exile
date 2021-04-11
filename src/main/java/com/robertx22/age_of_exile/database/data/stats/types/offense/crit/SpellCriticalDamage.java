package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.crit.SpellCriticalDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

public class SpellCriticalDamage extends Stat implements IExtraStatEffect {

    public static String GUID = "spell_critical_damage";

    public static SpellCriticalDamage getInstance() {
        return SpellCriticalDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects spell damage if it's a critical.";
    }

    @Override
    public IStatEffect getEffect() {
        return SpellCriticalDamageEffect.getInstance();
    }

    private SpellCriticalDamage() {
        this.base_val = 50;
        this.min_val = 0;
        this.max_val = 500;
        this.statGroup = StatGroup.MAIN;

        this.textIcon = "\u2739";
        this.textFormat = Formatting.DARK_PURPLE;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Spell Crit Damage";
    }

    private static class SingletonHolder {
        private static final SpellCriticalDamage INSTANCE = new SpellCriticalDamage();
    }
}

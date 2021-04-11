package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.crit.SpellCriticalHitEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

public class SpellCriticalHit extends Stat implements IExtraStatEffect {

    public static String GUID = "spell_critical_hit";

    public static SpellCriticalHit getInstance() {
        return SpellCriticalHit.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to multiply spell damage by critical damage";
    }

    @Override
    public IStatEffect getEffect() {
        return SpellCriticalHitEffect.getInstance();
    }

    private SpellCriticalHit() {
        this.base_val = 1;
        this.max_val = 100;
        this.min_val = 0;
        this.statGroup = StatGroup.MAIN;

        this.textIcon = "\u2739";
        this.textFormat = Formatting.LIGHT_PURPLE;
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
        return "Spell Crit Chance";
    }

    private static class SingletonHolder {
        private static final SpellCriticalHit INSTANCE = new SpellCriticalHit();
    }
}


package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.SpellDodgeEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class SpellDodge extends Stat implements IUsableStat {

    public static String GUID = "spell_dodge";

    public static SpellDodge getInstance() {
        return SpellDodge.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to ignore magic spell damage. Melee and ranged type spells don't count.";
    }

    private SpellDodge() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.statEffect = SpellDodgeEffect.getInstance();
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
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Spell Dodge";
    }

    @Override
    public float getMaxMulti() {
        return 0.9F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 200;
    }

    private static class SingletonHolder {
        private static final SpellDodge INSTANCE = new SpellDodge();
    }
}

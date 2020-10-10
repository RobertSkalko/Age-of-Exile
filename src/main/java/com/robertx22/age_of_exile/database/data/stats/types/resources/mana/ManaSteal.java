package com.robertx22.age_of_exile.database.data.stats.types.resources.mana;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.resource.ManaStealEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ManaSteal extends Stat implements IStatEffects {

    public static String GUID = "mana_steal";

    public static ManaSteal getInstance() {
        return ManaSteal.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Percent of basic attack DMG added to mana";
    }

    @Override
    public IStatEffect getEffect() {
        return ManaStealEffect.getInstance();
    }

    private ManaSteal() {
        this.statGroup = StatGroup.RESTORATION;
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
        return "Mana Steal";
    }

    private static class SingletonHolder {
        private static final ManaSteal INSTANCE = new ManaSteal();
    }
}

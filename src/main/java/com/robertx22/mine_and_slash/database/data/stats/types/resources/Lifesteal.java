package com.robertx22.mine_and_slash.database.data.stats.types.resources;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.resource.LifestealEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

public class Lifesteal extends Stat implements IStatEffects {

    public static String GUID = "lifesteal";

    public static Lifesteal getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Regeneration;
    }

    @Override
    public String locDescForLangFile() {
        return "Percent of basic attack DMG added to health";
    }

    @Override
    public IStatEffect getEffect() {
        return new LifestealEffect();
    }

    private Lifesteal() {
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
        return "Lifesteal";
    }

    private static class SingletonHolder {
        private static final Lifesteal INSTANCE = new Lifesteal();
    }
}

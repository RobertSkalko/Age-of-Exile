package com.robertx22.mine_and_slash.database.data.stats.types.resources;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.resource.MagicStealEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

public class MagicSteal extends Stat implements IStatEffects {

    public static String GUID = "magic_steal";

    private MagicSteal() {
    }

    public static MagicSteal getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Regeneration;
    }

    @Override
    public String locDescForLangFile() {
        return "Percent of basic attack DMG will restore magic shield.";
    }

    @Override
    public IStatEffect getEffect() {
        return MagicStealEffect.getInstance();
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
        return "Magic Steal";
    }

    private static class SingletonHolder {
        private static final MagicSteal INSTANCE = new MagicSteal();
    }
}

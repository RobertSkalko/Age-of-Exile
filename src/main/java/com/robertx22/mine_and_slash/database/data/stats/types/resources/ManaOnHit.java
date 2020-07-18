package com.robertx22.mine_and_slash.database.data.stats.types.resources;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.resource.ManaOnHitEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

public class ManaOnHit extends Stat implements IStatEffects {
    public static String GUID = "mana_on_hit";

    private ManaOnHit() {
    }

    @Override
    public StatScaling getScaling() {
        return StatScaling.SCALING;
    }

    public static ManaOnHit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Regeneration;
    }

    @Override
    public String locDescForLangFile() {
        return "Gives mana on basic attack";
    }

    @Override
    public IStatEffect getEffect() {
        return new ManaOnHitEffect();
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
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Mana on Hit";
    }

    private static class SingletonHolder {
        private static final ManaOnHit INSTANCE = new ManaOnHit();
    }
}
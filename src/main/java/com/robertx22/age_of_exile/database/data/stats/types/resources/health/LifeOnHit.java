package com.robertx22.age_of_exile.database.data.stats.types.resources.health;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.resource.LifeOnHitEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class LifeOnHit extends Stat implements IStatEffects {

    public static String GUID = "life_on_hit";

    public static LifeOnHit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Gives health on basic attack hit";
    }

    @Override
    public IStatEffect getEffect() {
        return new LifeOnHitEffect();
    }

    private LifeOnHit() {
        this.scaling = StatScaling.SCALING;
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
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Life on Hit";
    }

    private static class SingletonHolder {
        private static final LifeOnHit INSTANCE = new LifeOnHit();
    }
}

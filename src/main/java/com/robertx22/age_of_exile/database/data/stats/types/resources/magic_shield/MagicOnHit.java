package com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.resource.MagicShieldOnHitEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class MagicOnHit extends Stat implements IStatEffects {

    public static String GUID = "magic_on_hit";

    public static MagicOnHit getInstance() {
        return MagicOnHit.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Gives magic shield on basic attack hit";
    }

    @Override
    public IStatEffect getEffect() {
        return new MagicShieldOnHitEffect();
    }

    private MagicOnHit() {
        this.scaling = StatScaling.NORMAL;
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
        return "Magic on Hit";
    }

    private static class SingletonHolder {
        private static final MagicOnHit INSTANCE = new MagicOnHit();
    }
}


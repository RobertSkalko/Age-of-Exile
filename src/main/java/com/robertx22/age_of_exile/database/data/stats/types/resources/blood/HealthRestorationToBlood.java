package com.robertx22.age_of_exile.database.data.stats.types.resources.blood;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.game_changers.HealthRestorationToBloodEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class HealthRestorationToBlood extends Stat implements IStatEffects {
    public static String GUID = "hp_resto_to_blood";

    private HealthRestorationToBlood() {
        this.min_val = 0;
        this.scaling = StatScaling.NORMAL;
        this.statGroup = StatGroup.Misc;
    }

    public static HealthRestorationToBlood getInstance() {
        return HealthRestorationToBlood.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Refills your blood by a % of your non spell related health restoration effects. ";
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
        return "Blood Transfusion";
    }

    @Override
    public IStatEffect getEffect() {
        return HealthRestorationToBloodEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final HealthRestorationToBlood INSTANCE = new HealthRestorationToBlood();
    }
}


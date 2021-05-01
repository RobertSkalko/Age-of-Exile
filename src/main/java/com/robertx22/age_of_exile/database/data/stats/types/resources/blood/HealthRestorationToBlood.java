package com.robertx22.age_of_exile.database.data.stats.types.resources.blood;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.game_changers.HealthRestorationToBloodEffect;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

public class HealthRestorationToBlood extends Stat implements IExtraStatEffect {
    public static String GUID = "hp_resto_to_blood";

    private HealthRestorationToBlood() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.Misc;

        this.is_long = true;
    }

    public static HealthRestorationToBlood getInstance() {
        return HealthRestorationToBlood.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "";
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
        return Formatting.GRAY + "You refill your " + Blood.getInstance()
            .getIconNameFormat() + " by " + Formatting.GREEN + SpecialStats.VAL1 + Formatting.GRAY + "% of your non-spell health restoration effects.";
    }

    @Override
    public IStatEffect getEffect() {
        return HealthRestorationToBloodEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final HealthRestorationToBlood INSTANCE = new HealthRestorationToBlood();
    }
}


package com.robertx22.age_of_exile.database.data.stats.types.resources.blood;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.game_changers.BloodUserEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

public class BloodUser extends Stat implements IExtraStatEffect {
    public static String GUID = "blood_user";

    private BloodUser() {
        this.min_val = 0;
        this.scaling = StatScaling.NORMAL;
        this.statGroup = StatGroup.Misc;

    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.JUST_NAME;
    }

    public static BloodUser getInstance() {
        return BloodUser.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "You use blood instead of mana";
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
        return "Blood User";
    }

    @Override
    public IStatEffect getEffect() {
        return BloodUserEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final BloodUser INSTANCE = new BloodUser();
    }
}


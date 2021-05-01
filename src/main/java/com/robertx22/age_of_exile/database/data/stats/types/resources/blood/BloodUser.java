package com.robertx22.age_of_exile.database.data.stats.types.resources.blood;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.game_changers.BloodUserEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

public class BloodUser extends Stat implements IExtraStatEffect {
    public static String GUID = "blood_user";

    private BloodUser() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.Misc;
        this.is_long = true;

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
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return Formatting.GRAY + "You now use " + Blood.getInstance()
            .getIconNameFormat() + " instead of " + Mana.getInstance()
            .getIconNameFormat();
    }

    @Override
    public IStatEffect getEffect() {
        return BloodUserEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final BloodUser INSTANCE = new BloodUser();
    }
}


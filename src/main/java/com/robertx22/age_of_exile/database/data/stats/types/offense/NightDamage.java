package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.NightDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class NightDamage extends Stat implements IStatEffects {

    public static String GUID = "night_dmg";

    private NightDamage() {
        this.scaling = StatScaling.NONE;
    }

    public static NightDamage getInstance() {
        return NightDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases dmg at night.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Damage at Night";
    }

    @Override
    public IStatEffect getEffect() {
        return NightDamageEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final NightDamage INSTANCE = new NightDamage();
    }
}

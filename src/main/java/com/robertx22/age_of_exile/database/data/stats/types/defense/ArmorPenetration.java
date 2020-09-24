package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.ArmorPenetrationEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ArmorPenetration extends Stat implements IStatEffects {

    public static ArmorPenetration getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Penetration;
    }

    @Override
    public String getIconPath() {
        return "armor";
    }

    @Override
    public String locDescForLangFile() {
        return "Ignores x armor";
    }

    public static String GUID = "armor_penetration";

    private ArmorPenetration() {
        this.min_val = 0;
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
        return false;
    }

    @Override
    public IStatEffect getEffect() {
        return new ArmorPenetrationEffect();
    }

    @Override
    public String locNameForLangFile() {
        return "Armor Penetration";
    }

    private static class SingletonHolder {
        private static final ArmorPenetration INSTANCE = new ArmorPenetration();
    }
}


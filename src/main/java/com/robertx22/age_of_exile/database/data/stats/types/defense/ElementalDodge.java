package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.ElementalDodgeEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

public class ElementalDodge extends DodgeRating {

    public static String GUID = "ele_dodge";

    public static ElementalDodge getInstance() {
        return ElementalDodge.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to ignore elemental damage";
    }

    @Override
    public IStatEffect getEffect() {
        return ElementalDodgeEffect.getInstance();
    }

    protected ElementalDodge() {
        this.min_val = 0;
        this.scaling = StatScaling.NORMAL;
        this.statGroup = StatGroup.MAIN;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Elemental Dodge";
    }

    private static class SingletonHolder {
        private static final ElementalDodge INSTANCE = new ElementalDodge();
    }
}

package com.robertx22.age_of_exile.database.data.stats.types.elementals.all_damage;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.AllEleDmgEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class AllEleDmg extends Stat implements IStatEffects {

    public static String GUID = "all_ele_dmg";

    @Override
    public String locDescForLangFile() {
        return "Increases All Elemental DMG, both spells and attacks";
    }

    public AllEleDmg() {

    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public IStatEffect getEffect() {
        return new AllEleDmgEffect();
    }

    @Override
    public String locNameForLangFile() {
        return "All Elemental Damage";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }
}

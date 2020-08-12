package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.misc.ChangeDmgElementEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ChangeDmgElementStat extends Stat implements IStatEffects {

    public static ChangeDmgElementStat PHYS_TO_FROST = new ChangeDmgElementStat(Elements.Physical, Elements.Water);
    public static ChangeDmgElementStat PHYS_TO_FIRE = new ChangeDmgElementStat(Elements.Physical, Elements.Fire);
    public static ChangeDmgElementStat PHYS_TO_POISON = new ChangeDmgElementStat(Elements.Physical, Elements.Nature);
    public static ChangeDmgElementStat PHYS_TO_THUNDER = new ChangeDmgElementStat(Elements.Physical, Elements.Thunder);

    Elements from;
    Elements to;

    public ChangeDmgElementStat(Elements from, Elements to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Transforms element of damage.";
    }

    @Override
    public String locNameForLangFile() {
        return from.dmgName + " to " + to.dmgName;
    }

    @Override
    public String GUID() {
        return from.guidName + "_to_" + to.guidName;
    }

    @Override
    public IStatEffect getEffect() {
        return new ChangeDmgElementEffect(from, to);
    }
}

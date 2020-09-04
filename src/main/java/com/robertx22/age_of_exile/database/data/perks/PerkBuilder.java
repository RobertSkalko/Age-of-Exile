package com.robertx22.age_of_exile.database.data.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;

import java.util.Arrays;

public class PerkBuilder {

    public static Perk spell(BaseSpell spell) {
        Perk perk = new Perk();

        perk.spell = spell.GUID();
        perk.type = Perk.PerkType.SPELL;
        perk.identifier = spell.GUID();
        perk.icon = spell.getIconLoc()
            .toString();

        perk.addToSerializables();
        return perk;
    }

    public static Perk stat(String id, OptScaleExactStat stat) {
        Perk perk = new Perk();
        perk.stats = Arrays.asList(stat);
        perk.type = Perk.PerkType.STAT;
        perk.identifier = id;
        perk.icon = stat.getStat()
            .getIconLocation()
            .toString();
        perk.addToSerializables();
        return perk;
    }

    public static Perk stat(OptScaleExactStat stat) {
        return stat(stat.stat, stat);
    }

}

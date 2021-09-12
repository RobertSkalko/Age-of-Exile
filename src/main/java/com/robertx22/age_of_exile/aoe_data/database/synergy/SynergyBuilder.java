package com.robertx22.age_of_exile.aoe_data.database.synergy;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.synergy.Synergy;

import java.util.Arrays;

public class SynergyBuilder {

    public static Synergy of(String id, String name, String forspell, int maxlvls, StatModifier... stats) {

        Synergy s = new Synergy();

        s.id = id;
        s.spell_id = forspell;
        s.maxlvl = maxlvls;
        s.locname = name;
        s.stats = Arrays.asList(stats);

        s.addToSerializables();

        return s;
    }
}

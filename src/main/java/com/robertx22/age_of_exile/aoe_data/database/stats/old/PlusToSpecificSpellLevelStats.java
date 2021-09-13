package com.robertx22.age_of_exile.aoe_data.database.stats.old;

import com.robertx22.age_of_exile.aoe_data.database.stats.PlusSpellLevelStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.HashMap;

public class PlusToSpecificSpellLevelStats implements ExileRegistryInit {

    private static HashMap<String, PlusSpellLevelStat> MAP = new HashMap<>();

    public static PlusSpellLevelStat get(String spellid) {
        if (!MAP.containsKey(spellid)) {
            PlusSpellLevelStat s = new PlusSpellLevelStat(ExileDB.Spells()
                .getFromSerializables(new DataGenKey<>(spellid)));
            s.addToSerializables();
            MAP.put(spellid, s);
        }
        return MAP.get(spellid);
    }

    @Override
    public void registerAll() {

    }
}

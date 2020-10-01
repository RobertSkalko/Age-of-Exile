package com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base;

import com.robertx22.age_of_exile.aoe_data.database.stats.AutoDatapackStats;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.MMORPG;

public abstract class DatapackSpellStat extends DatapackStat {

    protected String spellname;
    public String spell = "";

    public DatapackSpellStat(String serializer) {
        super(serializer);
        if (MMORPG.RUN_DEV_TOOLS) {
            AutoDatapackStats.STATS_TO_ADD_TO_SERIALIZATION.put(GUID(), this);
        }
    }

    public Spell getSpell() {
        return SlashRegistry.Spells()
            .get(spell);
    }

}
package com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base;

import com.robertx22.age_of_exile.aoe_data.database.stats.AutoDatapackStats;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.MMORPG;

public abstract class DatapackSpellStat extends BaseDatapackStat {

    protected String spellname;
    public String spell = "";

    public DatapackSpellStat(String serializer) {
        super(serializer);
        if (MMORPG.RUN_DEV_TOOLS) {
            AutoDatapackStats.STATS_TO_ADD_TO_SERIALIZATION.add(this);
        }
    }

    public Spell getSpell() {
        return Database.Spells()
            .get(spell);
    }

}
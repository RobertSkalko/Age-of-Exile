package com.robertx22.age_of_exile.database.data.stats.datapacks.stats.base;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public abstract class DatapackSpellStat extends DatapackStat {

    protected String spellname;
    public String spell = "";

    public DatapackSpellStat(String serializer) {
        super(serializer);
    }

    public Spell getSpell() {
        return SlashRegistry.Spells()
            .get(spell);
    }

}
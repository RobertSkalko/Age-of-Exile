package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.AutoDatapackStats;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.library_of_exile.registry.DataGenKey;

// does nothing, just used by other things, like the "hasStat" spell condition
public class MarkerStat extends BaseDatapackStat {

    public static String SER_ID = "marker";

    transient String locname;
    transient String desc;

    public MarkerStat(DataGenKey<MarkerStat> id, Spell spell, String locname) {
        super(SER_ID);
        this.locname = spell.locNameForLangFile() + ": " + locname;
        this.desc = "This is a modifier for the spell. You can view the changes in the spell tooltip.";
        this.id = id.GUID();

        AutoDatapackStats.STATS_TO_ADD_TO_SERIALIZATION.add(this);

    }

    public MarkerStat() {
        super(SER_ID);
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.JUST_NAME;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locDescForLangFile() {
        return desc;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}


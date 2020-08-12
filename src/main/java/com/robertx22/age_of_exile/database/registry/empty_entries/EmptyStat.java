package com.robertx22.age_of_exile.database.registry.empty_entries;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class EmptyStat extends Stat {

    private EmptyStat() {
    }

    public static EmptyStat getInstance() {
        return SingletonHolder.INSTANCE;
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
        return "This stat was probably removed or renamed.";
    }

    @Override
    public String locNameForLangFile() {
        return "Unknown Stat";
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.EMPTY;
    }

    @Override
    public String GUID() {
        return "unknown_stat";
    }

    private static class SingletonHolder {
        private static final EmptyStat INSTANCE = new EmptyStat();
    }
}

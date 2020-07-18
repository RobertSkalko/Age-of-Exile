package com.robertx22.exiled_lib.registry.empty_entries;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

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

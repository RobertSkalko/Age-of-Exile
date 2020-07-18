package com.robertx22.exiled_lib.registry.empty_entries;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;

public class EmptyAffix extends Affix {

    private EmptyAffix() {
    }

    public static EmptyAffix getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "unknown_affix";
    }

    @Override
    public String locNameForLangFile() {
        return "Unknown Affix";
    }

    private static class SingletonHolder {
        private static final EmptyAffix INSTANCE = new EmptyAffix();
    }
}

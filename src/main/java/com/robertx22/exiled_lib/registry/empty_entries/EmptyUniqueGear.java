package com.robertx22.exiled_lib.registry.empty_entries;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;

import java.util.List;

public class EmptyUniqueGear implements IUnique {
    @Override
    public List<StatModifier> uniqueStats() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return null;
    }

    @Override
    public String locNameForLangFile() {
        return null;
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public BaseGearType getBaseGearType() {
        return null;
    }
}

package com.robertx22.age_of_exile.database.registry.empty_entries;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;

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

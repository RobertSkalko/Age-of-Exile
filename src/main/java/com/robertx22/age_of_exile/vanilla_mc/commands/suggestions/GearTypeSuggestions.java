package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.Database;

import java.util.ArrayList;
import java.util.List;

public class GearTypeSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {

        List<String> list = new ArrayList();
        for (BaseGearType slot : Database.GearTypes()
            .getAll()
            .values()) {
            list.add(slot.GUID());
        }
        list.add("random");

        return list;
    }

}


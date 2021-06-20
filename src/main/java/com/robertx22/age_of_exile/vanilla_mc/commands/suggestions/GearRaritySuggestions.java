package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.database.registry.Database;

import java.util.ArrayList;
import java.util.List;

public class GearRaritySuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {
        List<String> list = new ArrayList();

        Database.GearRarities()
            .getList()
            .forEach(x -> {
                if (!x.is_unique_item) {
                    list.add(x.GUID());
                }
            });
        list.add("random");

        return list;
    }

}


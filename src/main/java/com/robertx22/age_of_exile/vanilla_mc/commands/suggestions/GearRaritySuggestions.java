package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;

import java.util.ArrayList;
import java.util.List;

public class GearRaritySuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {
        List<String> list = new ArrayList();

        SlashRegistry.GearRarities()
            .getList()
            .forEach(x -> {
                if (!x.isUnique()) {
                    list.add(x.GUID());
                }
            });
        list.add("random");

        return list;
    }

}


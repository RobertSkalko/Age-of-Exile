package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSuggestions extends CommandSuggestions {

    ExileRegistryType type;

    public DatabaseSuggestions(ExileRegistryType type) {
        this.type = type;
    }

    @Override
    public List<String> suggestions() {
        List<String> list = new ArrayList<>();

        Database.getRegistry(type)
            .getList()
            .stream()
            .forEach(x -> {
                IGUID g = (IGUID) x;
                list.add(g.GUID());
            });

        list.add("random");
        return list;
    }

}

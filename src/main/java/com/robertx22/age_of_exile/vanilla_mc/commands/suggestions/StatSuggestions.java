package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;

import java.util.ArrayList;
import java.util.List;

public class StatSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {

        List<String> list = new ArrayList();
        for (Stat item : ExileDB.Stats()
            .getAll()
            .values()) {
            list.add(item.GUID());
        }

        return list;

    }

}


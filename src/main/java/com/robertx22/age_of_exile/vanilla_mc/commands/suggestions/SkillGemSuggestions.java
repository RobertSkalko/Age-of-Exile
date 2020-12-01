package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.database.registry.Database;

import java.util.List;
import java.util.stream.Collectors;

public class SkillGemSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {
        List<String> list = Database.SkillGems()
            .getList()
            .stream()
            .map(x -> x.GUID())
            .collect(Collectors.toList());
        list.add("random");
        return list;
    }

}


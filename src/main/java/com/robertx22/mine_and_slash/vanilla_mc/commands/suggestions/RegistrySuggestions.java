package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import com.robertx22.mine_and_slash.database.data.IGUID;

import java.util.List;
import java.util.stream.Collectors;

public class RegistrySuggestions extends CommandSuggestions {

    List<String> list;

    public RegistrySuggestions(List<? extends IGUID> items) {
        this.list = items.stream()
            .map(x -> x.GUID())
            .collect(Collectors.toList());
        this.list.add("random");
    }

    @Override
    public List<String> suggestions() {
        return list;
    }
}

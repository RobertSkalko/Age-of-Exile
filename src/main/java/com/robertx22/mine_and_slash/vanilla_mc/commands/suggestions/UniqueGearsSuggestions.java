package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import com.robertx22.exiled_lib.registry.SlashRegistry;

import java.util.List;
import java.util.stream.Collectors;

public class UniqueGearsSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {
        List<String> list = SlashRegistry.UniqueGears()
            .getList()
            .stream()
            .map(x -> x.GUID())
            .collect(Collectors.toList());
        list.add("random");
        return list;
    }

}


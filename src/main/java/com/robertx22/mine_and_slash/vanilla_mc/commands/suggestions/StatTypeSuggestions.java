package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatTypeSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {

        return Arrays.stream(ModType.values())
            .map(x -> x.name())
            .collect(Collectors.toList());

    }

}


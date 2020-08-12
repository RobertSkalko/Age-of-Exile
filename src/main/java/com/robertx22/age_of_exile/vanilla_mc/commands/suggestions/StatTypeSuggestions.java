package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

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


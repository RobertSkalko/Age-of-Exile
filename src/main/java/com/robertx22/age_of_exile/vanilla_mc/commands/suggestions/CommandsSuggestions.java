package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.uncommon.testing.CommandTests;

import java.util.ArrayList;
import java.util.List;

public class CommandsSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {
        List<String> list = new ArrayList<>(CommandTests.tests.keySet());
        return list;
    }

}

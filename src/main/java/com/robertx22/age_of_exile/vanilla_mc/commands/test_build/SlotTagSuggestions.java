package com.robertx22.age_of_exile.vanilla_mc.commands.test_build;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandSuggestions;

import java.util.ArrayList;
import java.util.List;

public class SlotTagSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {

        List<String> list = new ArrayList();
        for (BaseGearType.SlotTag slot : BaseGearType.SlotTag.values()) {
            list.add(slot.name());
        }

        return list;
    }

}

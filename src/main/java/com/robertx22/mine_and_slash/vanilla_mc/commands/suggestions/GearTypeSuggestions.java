package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.exiled_lib.registry.SlashRegistry;

import java.util.ArrayList;
import java.util.List;

public class GearTypeSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {

        List<String> list = new ArrayList();
        for (BaseGearType slot : SlashRegistry.GearTypes()
            .getAll()
            .values()) {
            list.add(slot.GUID());
        }
        list.add("random");

        return list;
    }

}


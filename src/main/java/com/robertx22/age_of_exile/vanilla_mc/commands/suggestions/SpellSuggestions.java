package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

import java.util.ArrayList;
import java.util.List;

public class SpellSuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {

        List<String> list = new ArrayList();
        for (BaseSpell spell : SlashRegistry.Spells()
            .getAll()
            .values()) {
            list.add(spell.GUID());
        }
        list.add("random");

        return list;
    }

}


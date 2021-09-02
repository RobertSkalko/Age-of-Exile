package com.robertx22.age_of_exile.aoe_data.database.runewords;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RunewordBuilder {

    //call this from a special unique builder for runeword uniques
    public static void of(String id, String uniqueid, List<RuneItem.RuneType> runes, String... gear_slots) {
        RuneWord word = new RuneWord();
        word.id = id;
        word.uniq_id = uniqueid;
        word.runes = runes.stream()
            .map(x -> x.id)
            .collect(Collectors.toList());
        word.addToSerializables();
        word.slots.addAll(Arrays.asList(gear_slots));
    }

}

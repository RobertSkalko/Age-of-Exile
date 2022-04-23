package com.robertx22.age_of_exile.aoe_data.database.runewords;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.runewords.RuneWordItem;
import com.robertx22.age_of_exile.mmorpg.SlashRef;

import java.util.Arrays;

public class RunewordBuilder {

    //call this from a special unique builder for runeword uniques
    public static void of(String id, String uniqueid, String... gear_slots) {
        RuneWord word = new RuneWord();
        word.id = id;
        word.item_id = SlashRef.id(RuneWordItem.getIdPath(id))
            .toString();
        word.uniq_id = uniqueid;
        word.addToSerializables();
        word.slots.addAll(Arrays.asList(gear_slots));
    }

}

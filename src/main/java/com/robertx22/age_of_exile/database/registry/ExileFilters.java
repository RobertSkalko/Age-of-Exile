package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.database.data.affixes.Affix;

import java.util.function.Predicate;

public class ExileFilters {

    public static Predicate<Affix> ofAffixType(Affix.Type type) {
        return x -> x.type == type;
    }

}

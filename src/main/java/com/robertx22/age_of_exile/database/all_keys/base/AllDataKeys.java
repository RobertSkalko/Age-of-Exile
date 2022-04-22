package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.age_of_exile.database.all_keys.RunewordKeys;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;

import java.util.HashMap;
import java.util.List;

public class AllDataKeys {

    public static HashMap<Class, List<DataKey<?>>> ALL = new HashMap<>();

    public static void initAll() {

        RunewordKeys.init();
        SpellKeys.init();
    }
}

package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.all_keys.RunewordKeys;
import com.robertx22.age_of_exile.database.all_keys.RunicSpellKeys;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllDataKeys {

    public static HashMap<Class, List<DataKey<?>>> ALL = new HashMap<>();

    public static List<RunewordKey> RUNEWORDS = new ArrayList<>();
    public static List<BaseGearKey> BASE_GEARS = new ArrayList<>();
    public static List<SpellKey> SPELLS = new ArrayList<>();

    public static void initAll() {

        BaseGearKeys.init();
        RunicSpellKeys.init();
        RunewordKeys.init();
        SpellKeys.init();
    }
}

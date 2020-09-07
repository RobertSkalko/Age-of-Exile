package com.robertx22.age_of_exile.database.data.spells.components.reg;

import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;

import java.util.HashMap;

public class SpellConditions {
    public static HashMap<String, EffectCondition> MAP = new HashMap<>();

    private static EffectCondition of(EffectCondition s) {
        MAP.put(s.GUID(), s);
        return s;

    }

}

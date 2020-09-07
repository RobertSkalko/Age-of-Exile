package com.robertx22.age_of_exile.database.data.spells.components.reg;

import com.robertx22.age_of_exile.database.data.spells.components.actions.DamageAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;

import java.util.HashMap;

public class SpellActions {
    public static HashMap<String, SpellAction> MAP = new HashMap<>();

    public static DamageAction DAMAGE = of(new DamageAction());

    private static <T extends SpellAction> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;

    }

}

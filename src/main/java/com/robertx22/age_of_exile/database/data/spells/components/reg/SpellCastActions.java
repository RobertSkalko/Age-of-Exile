package com.robertx22.age_of_exile.database.data.spells.components.reg;

import com.robertx22.age_of_exile.database.data.spells.components.cast_actions.CastAction;
import com.robertx22.age_of_exile.database.data.spells.components.cast_actions.ProjectileCastAction;

import java.util.HashMap;

public class SpellCastActions {
    public static HashMap<String, CastAction> MAP = new HashMap<>();

    public static CastAction PROJECTILE = of(new ProjectileCastAction());

    private static CastAction of(CastAction s) {
        MAP.put(s.GUID(), s);
        return s;

    }

}

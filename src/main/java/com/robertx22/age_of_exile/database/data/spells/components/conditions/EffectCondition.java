package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;
import java.util.List;

public abstract class EffectCondition extends BaseFieldNeeder implements IGUID {

    public EffectCondition(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public static TickRateCondition EVERY_X_TICKS;
    public static ChanceCondition CHANCE;
    public static CasterHasEffectCondition CASTER_HAS_POTION;

    public abstract boolean canActivate(SpellCtx ctx, MapHolder data);

    public static HashMap<String, EffectCondition> MAP = new HashMap<>();

    private static <T extends EffectCondition> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;
    }

    public static void init() {
        EVERY_X_TICKS = of(new TickRateCondition());
        CHANCE = of(new ChanceCondition());
        CASTER_HAS_POTION = of(new CasterHasEffectCondition());
    }
}


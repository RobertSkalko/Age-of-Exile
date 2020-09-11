package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;

import java.util.HashMap;
import java.util.List;

public abstract class EffectCondition extends BaseFieldNeeder implements IGUID {

    public EffectCondition(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public static OnTickCondition EVERY_X_TICKS;
    public static ChanceCondition CHANCE;
    public static CasterHasEffectCondition CASTER_HAS_POTION;

    public static OnCastCondition ON_CAST;
    public static OnExpireCondition ON_ENTITY_EXPIRE;
    public static OnHitCondition ON_HIT;
    public static CasterHasModifierCondition CASTER_HAS_SPELL_MOD;

    public abstract boolean canActivate(SpellCtx ctx, MapHolder data);

    public static HashMap<String, EffectCondition> MAP = new HashMap<>();

    private static <T extends EffectCondition> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;
    }

    public static void init() {
        EVERY_X_TICKS = of(new OnTickCondition());
        CHANCE = of(new ChanceCondition());
        CASTER_HAS_POTION = of(new CasterHasEffectCondition());

        ON_CAST = of(new OnCastCondition());
        ON_ENTITY_EXPIRE = of(new OnExpireCondition());
        ON_HIT = of(new OnHitCondition());

        CASTER_HAS_SPELL_MOD = of(new CasterHasModifierCondition());

    }
}


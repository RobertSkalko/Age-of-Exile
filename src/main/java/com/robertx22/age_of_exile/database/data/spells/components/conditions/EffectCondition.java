package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.HashMap;
import java.util.List;

public abstract class EffectCondition extends BaseFieldNeeder implements IGUID {

    public EffectCondition(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public static IsNotOnCooldownCondition IS_NOT_ON_COOLDOWN;
    public static EntityInRadiusCondition IS_ENTITY_IN_RADIUS;
    public static IsCasterCondition IS_TARGET_CASTER;
    public static OnTickCondition EVERY_X_TICKS;
    public static ChanceCondition CHANCE;
    public static CasterHasEffectCondition CASTER_HAS_POTION;

    public static OnCastCondition ON_CAST;
    public static OnExpireCondition ON_ENTITY_EXPIRE;
    public static OnHitCondition ON_HIT;
    public static CasterHasStatCondition CASTER_HAS_STAT;
    public static IsAllyCondition IS_TARGET_ALLY;
    public static TargetHasEffectCondition TARGET_HAS_POTION;
    public static DidNotAffectByEntity DID_NOT_AFFECT_ENTITY_ALREADY;
    public static DidNotAffectBySpellAlready DID_NOT_AFFECT_BY_SPELL_ALREADY;

    public abstract boolean canActivate(SpellCtx ctx, MapHolder data);

    public static HashMap<String, EffectCondition> MAP = new HashMap<>();

    private static <T extends EffectCondition> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;
    }

    public static void init() {
        IS_TARGET_CASTER = of(new IsCasterCondition());
        DID_NOT_AFFECT_BY_SPELL_ALREADY = of(new DidNotAffectBySpellAlready());
        DID_NOT_AFFECT_ENTITY_ALREADY = of(new DidNotAffectByEntity());
        IS_NOT_ON_COOLDOWN = of(new IsNotOnCooldownCondition());
        IS_ENTITY_IN_RADIUS = of(new EntityInRadiusCondition());
        TARGET_HAS_POTION = of(new TargetHasEffectCondition());
        IS_TARGET_ALLY = of(new IsAllyCondition());
        EVERY_X_TICKS = of(new OnTickCondition());
        CHANCE = of(new ChanceCondition());
        CASTER_HAS_POTION = of(new CasterHasEffectCondition());

        ON_CAST = of(new OnCastCondition());
        ON_ENTITY_EXPIRE = of(new OnExpireCondition());
        ON_HIT = of(new OnHitCondition());

        CASTER_HAS_STAT = of(new CasterHasStatCondition());

    }
}


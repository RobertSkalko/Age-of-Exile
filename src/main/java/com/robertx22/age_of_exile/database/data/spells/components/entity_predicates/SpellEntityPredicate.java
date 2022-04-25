package com.robertx22.age_of_exile.database.data.spells.components.entity_predicates;

import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.IsNotOnCooldownCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;

public abstract class SpellEntityPredicate extends BaseFieldNeeder implements IGUID {

    public SpellEntityPredicate(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract boolean is(SpellCtx ctx, LivingEntity target, MapHolder data);

    public static HashMap<String, SpellEntityPredicate> MAP = new HashMap<>();

    public static IsCasterPredicate IS_CASTER = of(new IsCasterPredicate());
    public static HasEffect HAS_EFFECT = of(new HasEffect());
    public static DidNotAffectBySpellAlready DID_NOT_AFFECT_BY_SPELL = of(new DidNotAffectBySpellAlready());
    public static DidNotAffectByEntity DID_NOT_AFFECT_BY_ENTITY = of(new DidNotAffectByEntity());
    public static IsNotOnCooldownCondition IS_NOT_ON_COOLDOWN = of(new IsNotOnCooldownCondition());

    private static <T extends SpellEntityPredicate> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;
    }
}

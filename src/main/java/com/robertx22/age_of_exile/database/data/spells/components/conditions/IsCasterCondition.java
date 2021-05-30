package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;

import java.util.Arrays;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class IsCasterCondition extends EffectCondition {

    public IsCasterCondition() {
        super(Arrays.asList(RADIUS, SELECTION_TYPE, ENTITY_PREDICATE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        if (ctx.target == null || ctx.caster == null) {
            return false;
        }
        return ctx.target == ctx.caster;
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "is_target_caster";
    }

}



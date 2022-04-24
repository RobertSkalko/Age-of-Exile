package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;

import java.util.Arrays;

public class DidNotHitAlready extends EffectCondition {

    public DidNotHitAlready() {
        super(Arrays.asList(MapField.CHANCE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        return !Load.spells(ctx.caster)
            .alreadyHit(ctx.sourceEntity, ctx.target);
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "did_not_hit_entity_already";
    }
}

package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;

import java.util.Arrays;

public class DidNotAffectByEntity extends EffectCondition {

    public DidNotAffectByEntity() {
        super(Arrays.asList(MapField.CHANCE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {

        if (ctx.target == null) {
            return false;
        }

        try {
            return !Load.spells(ctx.caster).mobsAffectedByEntity
                .alreadyHit(ctx.sourceEntity, ctx.target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "did_not_affect_by_entity_already";
    }
}

package com.robertx22.age_of_exile.database.data.spells.components.entity_predicates;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;

public class DidNotAffectByEntity extends SpellEntityPredicate {

    public DidNotAffectByEntity() {
        super(Arrays.asList(MapField.CHANCE));
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

    @Override
    public boolean is(SpellCtx ctx, LivingEntity target, MapHolder data) {
        return !Load.spells(ctx.caster).mobsAffectedByEntity
            .alreadyHit(ctx.sourceEntity, target);
    }
}

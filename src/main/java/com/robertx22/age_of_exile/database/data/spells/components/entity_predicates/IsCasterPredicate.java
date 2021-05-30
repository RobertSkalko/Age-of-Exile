package com.robertx22.age_of_exile.database.data.spells.components.entity_predicates;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;

public class IsCasterPredicate extends SpellEntityPredicate {

    public IsCasterPredicate() {
        super(Arrays.asList());
    }

    @Override
    public boolean is(SpellCtx ctx, LivingEntity target, MapHolder data) {
        return ctx.caster == target;
    }

    @Override
    public String GUID() {
        return "is_caster";
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        validate(d);
        return d;
    }
}

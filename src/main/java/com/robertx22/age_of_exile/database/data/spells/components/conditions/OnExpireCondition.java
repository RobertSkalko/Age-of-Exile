package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;

import java.util.Arrays;

public class OnExpireCondition extends EffectCondition {

    public OnExpireCondition() {
        super(Arrays.asList());
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        return ctx.activation == EntityActivation.ON_EXPIRE;
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "on_entity_expire";
    }
}


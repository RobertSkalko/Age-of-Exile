package com.robertx22.age_of_exile.database.data.spells.components.activated_on;

import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;

import java.util.Arrays;
import java.util.HashMap;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.TICK_RATE;

public class ActivatedOnTick extends ActivatedOn {

    public ActivatedOnTick() {
        super(Arrays.asList(TICK_RATE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, HashMap<String, Object> map) {
        int ticks = TICK_RATE.get(map)
            .intValue();
        return ctx.sourceEntity != null && ctx.sourceEntity.age % ticks == 0;
    }

    @Override
    public String GUID() {
        return "on_tick";
    }

}

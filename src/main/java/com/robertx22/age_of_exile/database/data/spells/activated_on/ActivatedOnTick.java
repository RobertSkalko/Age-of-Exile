package com.robertx22.age_of_exile.database.data.spells.activated_on;

import com.robertx22.age_of_exile.database.data.spells.contexts.DefaultSpellCtx;
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
        if (ctx instanceof DefaultSpellCtx) {
            DefaultSpellCtx dx = (DefaultSpellCtx) ctx;

            int ticks = TICK_RATE.get(map);
            return dx.sourceEntity != null && dx.sourceEntity.age % ticks == 0;
        }
        return false;
    }

    @Override
    public String GUID() {
        return "on_tick";
    }

}

package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;

import java.util.Arrays;

public class IsNotOnCooldownCondition extends EffectCondition {

    public IsNotOnCooldownCondition() {
        super(Arrays.asList(MapField.CHANCE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        try {
            if (ctx.target == null) {
                return false;
            }
            String id = data.get(MapField.COOLDOWN_ID);
            return !Load.Unit(ctx.target)
                .getCooldowns()
                .isOnCooldown(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public MapHolder create(String id) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.COOLDOWN_ID, id);
        return d;
    }

    @Override
    public String GUID() {
        return "is_not_on_cd";
    }
}


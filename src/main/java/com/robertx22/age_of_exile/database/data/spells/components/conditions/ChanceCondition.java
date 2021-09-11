package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.utils.RandomUtils;

import java.util.Arrays;

public class ChanceCondition extends EffectCondition {

    public ChanceCondition() {
        super(Arrays.asList(MapField.CHANCE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        Double chance = data.get(MapField.CHANCE);
        return RandomUtils.roll(chance, ctx.world.random);
    }

    public MapHolder create(Double chance) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.CHANCE, chance);
        return d;
    }

    @Override
    public String GUID() {
        return "chance";
    }
}

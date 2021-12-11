package com.robertx22.age_of_exile.database.data.spells.components.entity_predicates;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;

import java.util.Arrays;

public class HasEffect extends SpellEntityPredicate {

    public HasEffect() {
        super(Arrays.asList(MapField.POTION_ID));
    }

    public MapHolder create(EffectCtx effect) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.POTION_ID, effect.getEffectLocation()
            .toString());
        validate(d);
        return d;
    }

    @Override
    public boolean is(SpellCtx ctx, LivingEntity target, MapHolder data) {
        try {
            Effect effect = data.getPotion();

            return target.hasEffect(effect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String GUID() {
        return "has_effect";
    }
}

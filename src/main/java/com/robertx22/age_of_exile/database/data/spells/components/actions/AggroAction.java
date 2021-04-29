package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

import java.util.Arrays;
import java.util.Collection;

public class AggroAction extends SpellAction {

    public enum Type {
        AGGRO, DE_AGGRO
    }

    public AggroAction() {
        super(Arrays.asList(MapField.AGGRO_TYPE));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Type aggro = data.getAggro();

        targets.forEach(x -> {

            if (x instanceof MobEntity) {
                MobEntity anger = (MobEntity) x;

                if (aggro == Type.AGGRO) {
                    anger.setTarget(ctx.caster);
                } else {
                    if (anger.getTarget()
                        .equals(ctx.caster)) {
                        anger.setTarget(null);
                    }
                }
            }
        });

    }

    public MapHolder create(Type type) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.AGGRO_TYPE, type.name());
        return d;
    }

    @Override
    public String GUID() {
        return "aggro";
    }
}


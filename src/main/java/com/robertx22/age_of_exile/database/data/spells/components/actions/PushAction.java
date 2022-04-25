package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.PUSH_STRENGTH;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.PUSH_WAY;

public class PushAction extends SpellAction {

    public PushAction() {
        super(Arrays.asList(PUSH_STRENGTH, PUSH_WAY));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClientSide) {
            float str = data.get(PUSH_STRENGTH)
                .floatValue();
            DashUtils.Way way = data.getPushWay();

            targets.forEach(x -> DashUtils.dash(x, str, way));
        }
    }

    public MapHolder create(Double str, DashUtils.Way way) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(PUSH_STRENGTH, str);
        d.put(PUSH_WAY, way.name());
        return d;
    }

    @Override
    public String GUID() {
        return "push";
    }
}


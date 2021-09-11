package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Collection;

public class TeleportCasterToSightAction extends SpellAction {

    public TeleportCasterToSightAction() {
        super(Arrays.asList(MapField.DISTANCE));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Double distance = data.getOrDefault(MapField.DISTANCE, 10D);

        HitResult ray = ctx.caster.raycast(distance, 0.0F, false);
        Vec3d pos = ray.getPos();

        EntityUtils.setLoc(ctx.caster, pos, ctx.caster.yaw, ctx.caster.pitch);

    }

    public MapHolder create(Double distance) {
        MapHolder c = new MapHolder();
        c.put(MapField.DISTANCE, distance);
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "tp_caster_in_dir";
    }

}

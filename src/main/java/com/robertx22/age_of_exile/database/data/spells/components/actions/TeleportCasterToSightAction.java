package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.phys.HitResult;

import java.util.Arrays;
import java.util.Collection;

public class TeleportCasterToSightAction extends SpellAction {

    public TeleportCasterToSightAction() {
        super(Arrays.asList(MapField.DISTANCE));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Double distance = data.getOrDefault(MapField.DISTANCE, 10D);

        HitResult ray = ctx.caster.pick(distance, 0.0F, false);
        Vector3d pos = ray.getLocation();

        EntityUtils.setLoc(ctx.caster, pos, ctx.caster.yRot, ctx.caster.xRot);

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

package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.library_of_exile.utils.GeometryUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ParticleInRadiusAction extends SpellAction {

    public ParticleInRadiusAction() {
        super(Arrays.asList(PARTICLE_TYPE, RADIUS, PARTICLE_COUNT));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (ctx.sourceEntity.world.isClient) {

            DefaultParticleType particle = data.getParticle();

            float radius = data.get(RADIUS)
                .floatValue();
            int amount = data.get(PARTICLE_COUNT)
                .intValue();

            if (ctx.sourceEntity.age > 2) {
                for (int i = 0; i < amount; i++) {
                    Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(ctx.vecPos, radius);
                    ParticleUtils.spawn(particle, ctx.world, p);
                }
            }
        }
    }

    public MapHolder create(DefaultParticleType particle, Double count, Double radius) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(RADIUS, radius);
        dmg.put(PARTICLE_COUNT, count);
        dmg.put(PARTICLE_TYPE, Registry.PARTICLE_TYPE.getId(particle)
            .toString());
        return dmg;
    }

    @Override
    public String GUID() {
        return "particles_in_radius";
    }
}

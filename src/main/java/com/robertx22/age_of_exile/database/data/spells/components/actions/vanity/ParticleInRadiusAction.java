package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.library_of_exile.utils.GeometryUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ParticleInRadiusAction extends SpellAction {

    public enum Shape {
        CIRCLE, HORIZONTAL_CIRCLE, HORIZONTAL_CIRCLE_EDGE
    }

    public ParticleInRadiusAction() {
        super(Arrays.asList(PARTICLE_TYPE, RADIUS, PARTICLE_COUNT));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClientSide) {

            Shape shape = data.getParticleShape();

            BasicParticleType particle = data.getParticle();

            float radius = data.get(RADIUS)
                .floatValue();

            radius *= ctx.calculatedSpellData.area_multi;

            float height = data.getOrDefault(HEIGHT, 0D)
                .floatValue();
            int amount = data.get(PARTICLE_COUNT)
                .intValue();

            amount *= ctx.calculatedSpellData.area_multi;

            ParticleMotion motion = null;

            try {
                motion = ParticleMotion.valueOf(data.get(MOTION));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                motion = ParticleMotion.None;
            }

            float yrand = data.getOrDefault(Y_RANDOM, 0D)
                .floatValue();

            float motionMulti = data.getOrDefault(MOTION_MULTI, 1D)
                .floatValue();

            if (shape == Shape.CIRCLE) {
                if (ctx.sourceEntity.tickCount > 1) {
                    for (int i = 0; i < amount; i++) {

                        // todo unsure if this helps
                        Vector3d pos = ctx.vecPos;
                        Vector3d vel = ctx.sourceEntity.getDeltaMovement();
                        pos = new Vector3d(pos.x - vel.x / 2F, pos.y - vel.y / 2 + height, pos.z - vel.z / 2);

                        Vector3d p = GeometryUtils.getRandomPosInRadiusCircle(pos, radius);
                        ParticleUtils.spawn(particle, ctx.world, p, motion.getMotion(p, ctx)
                            .scale(motionMulti));
                    }
                }
            } else if (shape == Shape.HORIZONTAL_CIRCLE) {
                for (int i = 0; i < amount; i++) {
                    float yRandom = (int) RandomUtils.RandomRange(0, yrand);
                    Vector3d p = GeometryUtils.getRandomHorizontalPosInRadiusCircle(ctx.vecPos.x(), ctx.vecPos.y() + height + yRandom, ctx.vecPos.z(), radius);
                    ParticleUtils.spawn(particle, ctx.world, p, motion.getMotion(p, ctx)
                        .scale(motionMulti));
                }
            } else if (shape == Shape.HORIZONTAL_CIRCLE_EDGE) {
                for (int i = 0; i < amount; i++) {
                    float yRandom = (int) RandomUtils.RandomRange(0, yrand);
                    Vector3d p = randomEdgeCirclePos(ctx.vecPos.x(), ctx.vecPos.y() + height + yRandom, ctx.vecPos.z(), radius);
                    ParticleUtils.spawn(particle, ctx.world, p, motion.getMotion(p, ctx)
                        .scale(motionMulti));
                }
            }
        }
    }

    public static Vector3d randomEdgeCirclePos(double x, double y, double z, float radius) {
        double angle = Math.random() * Math.PI * 2;
        double xpos = x + Math.cos(angle) * radius;
        double zpos = z + Math.sin(angle) * radius;
        return new Vector3d(xpos, y, zpos);
    }

    public MapHolder create(BasicParticleType particle, Double count, Double radius) {
        return create(particle, count, radius, ParticleMotion.None);
    }

    public MapHolder create(BasicParticleType particle, Double count, Double radius, ParticleMotion motion) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(RADIUS, radius);
        dmg.put(PARTICLE_COUNT, count);
        dmg.put(PARTICLE_TYPE, Registry.PARTICLE_TYPE.getKey(particle)
            .toString());
        dmg.put(MOTION, motion.name());
        return dmg;
    }

    @Override
    public String GUID() {
        return "particles_in_radius";
    }
}

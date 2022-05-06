package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.PARTICLE_COUNT;

public class IceSpikeParticlesAction extends SpellAction {

    public IceSpikeParticlesAction() {
        super(Arrays.asList());
    }

    static IParticleData PARTICLE = new ItemParticleData(ParticleTypes.ITEM, Items.ICE.getDefaultInstance());

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        IParticleData PARTICLE = new ItemParticleData(ParticleTypes.ITEM, Items.ICE.getDefaultInstance());

        Vector3d pos = ctx.vecPos.add(0, 1, 0);

        int amount = data.getOrDefault(MapField.COUNT, 50D)
            .intValue();

        Random random = ctx.caster.getRandom();

        for (int i = 0; i < amount; i++) {

            Vector3d vel = new Vector3d(
                random.nextGaussian(),
                random.nextGaussian() / 2,
                random.nextGaussian()
            ).normalize()
                .multiply(1, 1, 1);

            ParticleUtils.spawn(ctx.world, pos, PARTICLE, vel);

        }

    }

    @Override
    public String GUID() {
        return "ice_burst_particles";
    }

    public MapHolder create(Double count) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(PARTICLE_COUNT, count);
        return dmg;
    }
}

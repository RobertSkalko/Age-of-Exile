package com.robertx22.age_of_exile.database.data.spells.entities.cloud;

import com.robertx22.age_of_exile.database.data.spells.SpellUtils;
import com.robertx22.age_of_exile.database.data.spells.entities.bases.BaseCloudEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThunderstormEntity extends BaseCloudEntity {

    public ThunderstormEntity(World world) {
        super(ModRegistry.ENTITIES.THUNDERSTORM, world);
    }

    public ThunderstormEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    public void initSpellEntity() {

    }

    @Override
    public void onHit(LivingEntity entity) {
        this.dealSpellDamageTo(entity, new Options().knockbacks(false));

        SpellUtils.summonLightningStrike(entity);

    }

    @Override
    public void summonFallParticle(Vec3d p) {

        ParticleUtils.spawn(ParticleTypes.FALLING_WATER, world, p);

    }

}
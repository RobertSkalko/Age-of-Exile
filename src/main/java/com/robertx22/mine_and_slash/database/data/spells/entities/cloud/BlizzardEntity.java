package com.robertx22.mine_and_slash.database.data.spells.entities.cloud;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.BaseCloudEntity;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlizzardEntity extends BaseCloudEntity {

    public BlizzardEntity(World world) {
        super(ModRegistry.ENTITIES.BLIZZARD, world);
    }

    public BlizzardEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    public void initSpellEntity() {

    }

    @Override
    public void onHit(LivingEntity entity) {

        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0f, 1.0f);

        DamageEffect dmg = dealSpellDamageTo(entity, new Options().knockbacks(false)
            .activatesEffect(false));

        dmg.Activate();

    }

    @Override
    public void summonFallParticle(Vec3d p) {
        ParticleUtils.spawn(ParticleTypes.ITEM_SNOWBALL, world, p);

    }

}
package com.robertx22.mine_and_slash.database.data.spells.entities.cloud;

import com.robertx22.mine_and_slash.database.data.spells.SpellUtils;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.BaseCloudEntity;
import com.robertx22.mine_and_slash.mmorpg.registers.common.EntityRegister;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class ThunderstormEntity extends BaseCloudEntity {

    public ThunderstormEntity(World world) {
        super(EntityRegister.THUNDERSTORM, world);
    }

    public ThunderstormEntity(EntityType type, World world) {
        super(type, world);
    }

    public ThunderstormEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(EntityRegister.THUNDERSTORM, world);
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
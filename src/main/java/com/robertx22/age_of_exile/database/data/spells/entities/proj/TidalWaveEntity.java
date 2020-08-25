package com.robertx22.age_of_exile.database.data.spells.entities.proj;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.BaseElementalBoltEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SoundUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TidalWaveEntity extends BaseElementalBoltEntity {

    public TidalWaveEntity(EntityType<? extends TidalWaveEntity> type, World world) {
        super(type, world);
    }

    public TidalWaveEntity(World worldIn) {

        super(ModRegistry.ENTITIES.TIDAL_WAVE, worldIn);

    }

    @Override
    public void initSpellEntity() {
        this.setNoGravity(false);
        this.setDeathTime(40);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public Elements element() {
        return Elements.Water;
    }

    @Override
    public void onHit(LivingEntity entity) {
        dealSpellDamageTo(entity);

        SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 0.8F, 1F);
    }

    @Override
    public void onTick() {
        if (world.isClient) {
            if (this.age > 2) {
                for (int i = 0; i < 25; i++) {
                    Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getPos(), 0.2F);
                    ParticleUtils.spawn(ModRegistry.PARTICLES.BUBBLE, world, p);
                }
                if (age % 2 == 0) {
                    ParticleUtils.spawn(ParticleTypes.DRIPPING_WATER, world, getPos());

                }
            }
        }

    }

}
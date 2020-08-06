package com.robertx22.mine_and_slash.database.data.spells.entities.single_target_bolt;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.BaseElementalBoltEntity;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PoisonBallEntity extends BaseElementalBoltEntity {

    public PoisonBallEntity(EntityType<? extends PoisonBallEntity> type, World world) {
        super(type, world);
    }

    public PoisonBallEntity(World worldIn) {

        super(ModRegistry.ENTITIES.POISON_BALL, worldIn);

    }

    @Override
    public Elements element() {
        return Elements.Nature;
    }

    @Override
    public void onHit(LivingEntity entity) {
        dealSpellDamageTo(entity);

        SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 0.8F, 1F);
    }

    @Override
    public void onTick() {

        if (world.isClient) {
            if (this.age > 1) {
                for (int i = 0; i < 3; i++) {
                    Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getPos(), 0.15F);
                    ParticleUtils.spawn(ParticleTypes.ITEM_SLIME, world, p);
                }
            }
        }

    }

}
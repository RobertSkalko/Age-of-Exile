package com.robertx22.age_of_exile.database.data.spells.entities.single_target_bolt;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.BaseElementalBoltEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SoundUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FrostballEntity extends BaseElementalBoltEntity {

    public FrostballEntity(EntityType<? extends FrostballEntity> type, World world) {
        super(type, world);
    }

    public FrostballEntity(World worldIn) {

        super(ModRegistry.ENTITIES.FROSTBOLT, worldIn);

    }

    @Override
    public Elements element() {
        return Elements.Water;
    }

    @Override
    public void onHit(LivingEntity entity) {

        SpellDamageEffect dmg = dealSpellDamageTo(entity, new Options().activatesEffect(false));

        dmg.Activate();

        SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 0.8F, 1F);

    }

    @Override
    public void onTick() {

        if (world.isClient) {
            if (this.age > 1) {
                for (int i = 0; i < 2; i++) {
                    Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getPos(), 0.2F);
                    ParticleUtils.spawn(ParticleTypes.ITEM_SNOWBALL, world, p);
                }
            }

        }

    }

}
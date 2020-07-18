package com.robertx22.mine_and_slash.database.data.spells.entities.proj;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.BaseElementalBoltEntity;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
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

public class ThrowFlameEntity extends BaseElementalBoltEntity {

    public ThrowFlameEntity(EntityType<? extends ThrowFlameEntity> type, World world) {
        super(type, world);
    }

    public ThrowFlameEntity(World worldIn) {
        super(ModRegistry.ENTITIES.THROW_FLAMES, worldIn);
    }

    @Override
    public void initSpellEntity() {
        this.setNoGravity(true);
        this.setDeathTime(120);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.BLAZE_POWDER);
    }

    @Override
    public Elements element() {
        return Elements.Fire;
    }

    @Override
    public void onHit(LivingEntity entity) {
        dealSpellDamageTo(entity);
        SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 0.8F, 1F);
    }

    LivingEntity target;

    @Override
    public void onTick() {
        if (world.isClient) {
            if (this.age > 1) {
                for (int i = 0; i < 2; i++) {
                    Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getPosVector(), 0.1F);
                    ParticleUtils.spawn(ParticleTypes.FLAME, world, p);
                }
            }

        }
    }

}

package com.robertx22.age_of_exile.database.data.spells.entities.bases;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class BaseElementalBoltEntity extends EntityBaseProjectile {

    @Environment(EnvType.CLIENT)
    @Override
    public ItemStack getItem() {
        return new ItemStack(this.element().projectileItem);
    }

    public abstract Elements element();

    @Override
    public void initSpellEntity() {
        this.setNoGravity(true);
        this.setDeathTime(60);
    }

    public BaseElementalBoltEntity(EntityType<? extends Entity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public double radius() {
        return 0.5D;
    }

    public abstract void onHit(LivingEntity entity);

    @Override
    protected void onImpact(HitResult result) {

        LivingEntity entityHit = getEntityHit(result, 0.3D);

        if (entityHit != null) {
            if (world.isClient) {
                SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 1F, 0.9F);
            }

            onHit(entityHit);

        } else {
            if (world.isClient) {
                SoundUtils.playSound(this, SoundEvents.BLOCK_STONE_HIT, 0.7F, 0.9F);
            }
        }

        this.remove();
    }

}
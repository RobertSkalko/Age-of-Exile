package com.robertx22.age_of_exile.mobs.chickens;

import com.robertx22.age_of_exile.mobs.OnTickRandomSpeedBoost;
import com.robertx22.age_of_exile.mobs.ai.NightAttackGoal;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseChicken extends HostileEntity {
    public BaseChicken(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    int angryTicks = 0;

    @Override
    public void tick() {
        if (OnTickRandomSpeedBoost.onTickTryAnger(this, angryTicks)) {
            angryTicks = 100;
        }
        angryTicks--;
        super.tick();
    }

    boolean warnedOthers = false;

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getHealth() >= this.getMaxHealth() * 0.9F) {
            Entity attacker = source.getAttacker();
            if (attacker instanceof LivingEntity) {
                if (attacker != getAttacker()) {
                    if (!warnedOthers) {
                        OnTickRandomSpeedBoost.onAttackedAngerNearbyMobs(this, (LivingEntity) attacker);
                        warnedOthers = true;
                    }
                }
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    /*
        @Override
        public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
            return true;
        }
     */

    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    public float flapSpeed = 1.0F;
    public int eggLayTime;
    public boolean jockey;

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation = (float) ((double) this.maxWingDeviation + (double) (this.onGround ? -1 : 4) * 0.3D);
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.onGround && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }

        this.flapSpeed = (float) ((double) this.flapSpeed * 0.9D);
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flapProgress += this.flapSpeed * 2.0F;
        if (!this.world.isClient && this.isAlive() && !this.isBaby() && --this.eggLayTime <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.EGG);
            this.eggLayTime = this.random.nextInt(6000) + 6000;
        }

    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(4, new NightAttackGoal(this, 1.2F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 14));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));

    }

    @Override
    protected Identifier getLootTableId() {
        return EntityType.CHICKEN.getLootTableId(); // TODO, add loot tables later
    }

}

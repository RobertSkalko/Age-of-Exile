package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.mobs.ai.SpellAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class BaseMage extends SkeletonEntity {

    public abstract String getSpell();

    public BaseMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);

    }

    @Override
    protected void initGoals() {
        super.initGoals();

        initMyMobGoals();

    }

    public void initMyMobGoals() {
        goalSelector.add(2, new SpellAttackGoal(getSpell(), this, 15));
    }

    @Override
    public void updateAttackType() {
        // replace this method so it doesnt add bow or melee attack goals
    }

    @Override
    protected boolean isAffectedByDaylight() {
        return false; // dont burn
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITCH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WITCH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITCH_DEATH;
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        // dont
    }

    @Override
    protected Identifier getLootTableId() {
        return EntityType.WITCH.getLootTableId(); // TODO, add loot tables later
    }

}
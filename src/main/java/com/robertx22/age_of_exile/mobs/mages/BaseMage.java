package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.mobs.ai.SpellAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public abstract class BaseMage extends SkeletonEntity {

    public abstract BaseSpell getSpell();

    public BaseMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        goalSelector.add(2, new SpellAttackGoal(getSpell(), this, 1, 50, 20));

    }

    @Override
    public void updateAttackType() {
        // replace this method so it doesnt add bow or melee attack goals
    }

    @Override
    protected boolean isInDaylight() {
        return false; // dont burn
    }
}
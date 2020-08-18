package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.divine.HealingAuraSpell;
import com.robertx22.age_of_exile.mobs.ai.SpellAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class HealerMage extends BaseMage {

    public HealerMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public BaseSpell getSpell() {
        return HealingAuraSpell.getInstance();
    }

    @Override
    public void initMyMobGoals() {
        goalSelector.add(2, new SpellAttackGoal(getSpell(), this, 1, 200, 3));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal(this, MobEntity.class, true));

        initMyMobGoals();

    }

}

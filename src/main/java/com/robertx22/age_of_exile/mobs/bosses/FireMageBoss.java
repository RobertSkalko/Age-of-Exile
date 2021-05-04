package com.robertx22.age_of_exile.mobs.bosses;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.BossSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.IntSpells;
import com.robertx22.age_of_exile.mobs.ai.SpellAttackGoal;
import com.robertx22.age_of_exile.mobs.mages.BaseMage;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class FireMageBoss extends BaseMage {

    public FireMageBoss(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public String getSpell() {
        return IntSpells.FIREBALL_ID;
    }

    @Override
    public void initMyMobGoals() {
        goalSelector.add(2, new SpellAttackGoal(getSpell(), this, 15).addOtherSpell(BossSpells.FIRE_BOMBS));
    }

}
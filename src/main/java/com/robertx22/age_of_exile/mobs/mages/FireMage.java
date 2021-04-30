package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.IntSpells;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class FireMage extends BaseMage {

    public FireMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public String getSpell() {
        return IntSpells.FIREBALL_ID;
    }
}
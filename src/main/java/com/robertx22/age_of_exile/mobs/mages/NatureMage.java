package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.nature.PoisonBallSpell;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class NatureMage extends BaseMage {

    public NatureMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public BaseSpell getSpell() {
        return PoisonBallSpell.getInstance();
    }
}


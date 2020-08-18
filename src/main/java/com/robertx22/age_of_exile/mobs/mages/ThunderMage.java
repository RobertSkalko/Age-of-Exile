package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.storm.ThunderspearSpell;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class ThunderMage extends BaseMage {

    public ThunderMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public BaseSpell getSpell() {
        return ThunderspearSpell.getInstance();
    }
}


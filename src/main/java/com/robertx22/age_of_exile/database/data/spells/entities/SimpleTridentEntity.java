package com.robertx22.age_of_exile.database.data.spells.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class SimpleTridentEntity extends SimpleProjectileEntity {

    public SimpleTridentEntity(EntityType<? extends Entity> type, World worldIn) {
        super(type, worldIn);
    }

}


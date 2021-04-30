package com.robertx22.age_of_exile.mmorpg.registers.common;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.SkeletonEntity;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class MobAttributes {

    public static void register() {

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.HEALER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_MAGE_BOSS, SkeletonEntity.createAbstractSkeletonAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 100));

    }
}

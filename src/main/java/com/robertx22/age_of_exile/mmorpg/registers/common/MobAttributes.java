package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mobs.bosses.GolemBossEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class MobAttributes {

    public static void register() {

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.HEALER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));

        FabricDefaultAttributeRegistry.register(ENTITIES.GOLEM_BOSS, GolemBossEntity.createAttributes());

    }
}

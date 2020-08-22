package com.robertx22.age_of_exile.mmorpg.registers.common;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class MobAttributes {

    public static DefaultAttributeContainer.Builder slime() {
        return MagmaCubeEntity.createMagmaCubeAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.3F);
    }

    public static DefaultAttributeContainer.Builder zombie() {
        return ZombieEntity.createZombieAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.28F);
    }

    public static void register() {

        FabricDefaultAttributeRegistry.register(ENTITIES.ARCANE_SLIME, slime());
        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_SLIME, slime());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_SLIME, slime());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_SLIME, slime());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_SLIME, slime());

        FabricDefaultAttributeRegistry.register(ENTITIES.ARCANE_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_SPIDER, SpiderEntity.createSpiderAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.ARCANE_ZOMBIE, zombie());
        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_ZOMBIE, zombie());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_ZOMBIE, zombie());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_ZOMBIE, zombie());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_ZOMBIE, zombie());

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.HEALER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_SKELETON, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_SKELETON, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_SKELETON, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_SKELETON, SkeletonEntity.createAbstractSkeletonAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_CHICKEN, SpiderEntity.createSpiderAttributes()
            .add(GENERIC_MOVEMENT_SPEED, 0.35F));

    }
}

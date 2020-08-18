package com.robertx22.age_of_exile.mmorpg.registers.common;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class MobAttributes {

    public static void register() {

        FabricDefaultAttributeRegistry.register(ENTITIES.ARCANE_SLIME, MagmaCubeEntity.createMagmaCubeAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_SLIME, MagmaCubeEntity.createMagmaCubeAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_SLIME, MagmaCubeEntity.createMagmaCubeAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_SLIME, MagmaCubeEntity.createMagmaCubeAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_SLIME, MagmaCubeEntity.createMagmaCubeAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.ARCANE_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_SPIDER, SpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_SPIDER, SpiderEntity.createSpiderAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.ARCANE_ZOMBIE, ZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_ZOMBIE, ZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_ZOMBIE, ZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_ZOMBIE, ZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_ZOMBIE, ZombieEntity.createZombieAttributes());

        FabricDefaultAttributeRegistry.register(ENTITIES.FIRE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.WATER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.NATURE_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.THUNDER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ENTITIES.HEALER_MAGE, SkeletonEntity.createAbstractSkeletonAttributes());

    }
}

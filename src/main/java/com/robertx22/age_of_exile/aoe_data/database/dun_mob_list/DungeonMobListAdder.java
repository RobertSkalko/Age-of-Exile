package com.robertx22.age_of_exile.aoe_data.database.dun_mob_list;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.entity.EntityType;

public class DungeonMobListAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        DungeonMobListBuilder.of("mossy")
            .addMob(EntityType.SKELETON)
            .addMob(EntityType.ZOMBIE)
            .addMob(EntityType.PILLAGER, 200)
            .addMob(EntityType.STRAY, 200)
            .addMob(EntityType.WITCH, 50)

            .addBoss(EntityType.WITHER_SKELETON)
            .build();

        DungeonMobListBuilder.of("undead")
            .addMob(EntityType.SKELETON)
            .addMob(EntityType.ZOMBIE)
            .addMob(EntityType.STRAY, 200)

            .addBoss(EntityType.WITHER_SKELETON)
            .build();

        DungeonMobListBuilder.of("nether")
            .addMob(EntityType.WITHER_SKELETON)
            .addMob(EntityType.BLAZE, 200)
            .addMob(EntityType.MAGMA_CUBE, 50)

            .addBoss(EntityType.WITHER_SKELETON)
            .build();

    }
}

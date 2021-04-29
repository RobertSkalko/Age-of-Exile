package com.robertx22.age_of_exile.aoe_data.database.dun_mob_list;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import net.minecraft.entity.EntityType;

public class DungeonMobListAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

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

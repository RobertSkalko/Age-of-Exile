package com.robertx22.age_of_exile.database.data.spells.entities.trident;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class ThunderspearEntity extends BaseTridentEntity {

    public ThunderspearEntity(World world) {
        super(ModRegistry.ENTITIES.THUNDER_SPEAR, world);
    }

    public ThunderspearEntity(EntityType type, World world) {
        super(type, world);
    }

}

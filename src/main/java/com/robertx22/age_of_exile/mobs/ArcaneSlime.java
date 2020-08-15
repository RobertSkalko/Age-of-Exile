package com.robertx22.age_of_exile.mobs;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ArcaneSlime extends MagmaCubeEntity {

    public ArcaneSlime(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);
    }

    public ArcaneSlime(World world) {
        super(ModRegistry.ENTITIES.ARCANE_SLIME, world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

}

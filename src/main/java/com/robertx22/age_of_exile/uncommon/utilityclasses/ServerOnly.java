package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class ServerOnly {

    public static Entity getEntityByUUID(World world, UUID id) {

        if (world instanceof ServerWorld) {
            return ((ServerWorld) world).getEntity(id);
        }

        return null;

    }

}

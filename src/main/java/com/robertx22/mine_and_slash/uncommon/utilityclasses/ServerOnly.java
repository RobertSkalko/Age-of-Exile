package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class ServerOnly {
    @Nullable
    public static Entity getEntityByUUID(World world, UUID id) {

        if (world instanceof ServerWorld) {
            return ((ServerWorld) world).getEntity(id);
        }

        return null;

    }
}

package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.UUID;

public class ClientOnly {

    public static Entity getEntityByUUID(World world, UUID id) {

        if (world instanceof ClientWorld) {
            for (Entity entity : ((ClientWorld) world).getEntities()) {
                if (entity.getUuid()
                    .equals(id)) {

                    return entity;
                }
            }
        }
        return null;

    }

    public static PlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }
}

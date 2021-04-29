package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.dimension.gui.MapsScreen;
import com.robertx22.age_of_exile.gui.screens.race_select.RaceSelectScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
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

    public static PlayerEntity getPlayerById(UUID id) {

        try {
            return MinecraftClient.getInstance().world.getPlayerByUuid(id);
        } catch (Exception e) {

        }
        return null;
    }

    public static PlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }

    public static void pressUseKey() {
        MinecraftClient.getInstance().options.keyUse.setPressed(true);
    }

    public static void openMapsScreen(BlockPos pos) {
        MinecraftClient.getInstance()
            .openScreen(new MapsScreen(pos));
    }

    public static void stopUseKey() {
        MinecraftClient.getInstance().options.keyUse.setPressed(false);
    }

    public static void openRaceSelection() {
        if (MinecraftClient.getInstance().currentScreen == null) {
            MinecraftClient.getInstance()
                .openScreen(new RaceSelectScreen());
        }
    }

}

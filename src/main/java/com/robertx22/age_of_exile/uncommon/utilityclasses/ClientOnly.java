package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.gui.screens.dungeon.DungeonInfoScreen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class ClientOnly {

    public static int ticksSinceChatWasOpened = 0;

    public static Entity getEntityByUUID(World world, UUID id) {

        if (world instanceof ClientWorld) {
            for (Entity entity : ((ClientWorld) world).entitiesForRendering()) {
                if (entity.getUUID()
                    .equals(id)) {

                    return entity;
                }
            }
        }
        return null;

    }

    public static PlayerEntity getPlayerById(UUID id) {

        try {
            return Minecraft.getInstance().level.getPlayerByUUID(id);
        } catch (Exception e) {

        }
        return null;
    }

    public static PlayerEntity getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void pressUseKey() {
        Minecraft.getInstance().options.keyUse.setDown(true);
    }

    public static void openMapsScreen(BlockPos pos) {
        Minecraft.getInstance()
            .setScreen(new DungeonInfoScreen(pos, Load.playerRPGData(Minecraft.getInstance().player).maps.dungeonData));

    }

    public static void stopUseKey() {
        Minecraft.getInstance().options.keyUse.setDown(false);
    }

}

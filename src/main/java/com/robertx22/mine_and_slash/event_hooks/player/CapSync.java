package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.capability.bases.CapSyncUtil;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapSync {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDimChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        try {
            CapSyncUtil.syncAll(event.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        try {
            CapSyncUtil.syncAll(event.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        try {
            CapSyncUtil.syncAll(event.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

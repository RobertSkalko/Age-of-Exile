package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StopCastingIfInteract {

    private static void stop(PlayerEntity player) {
        if (player.world.isClient) {
            return;
        }
        PlayerSpellCap.ISpellsCap data = Load.spells(player);

        if (data.getCastingData()
            .isCasting()) {
            data.getCastingData()
                .cancelCast(player);
            data.syncToClient(player);
        }
    }

    @SubscribeEvent
    public static void onInteract(AttackEntityEvent event) {
        stop(event.getPlayer());
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.RightClickItem event) {
        stop(event.getPlayer());
    }

}


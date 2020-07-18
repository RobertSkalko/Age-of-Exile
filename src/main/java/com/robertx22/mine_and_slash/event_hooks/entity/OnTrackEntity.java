package com.robertx22.mine_and_slash.event_hooks.entity;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnTrackEntity {

    @SubscribeEvent
    public static void onEntityTrack(PlayerEvent.StartTracking event) {

        Entity entity = event.getTarget();

        try {
            if (entity instanceof LivingEntity) {

                if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                    return;
                }

                if (entity.isPartOf(event.getPlayer()) == false) {

                    MMORPG.sendToClient(
                        Unit.getUpdatePacketFor((LivingEntity) entity, Load.Unit(entity)),
                        (ServerPlayerEntity) event.getPlayer()
                    );

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

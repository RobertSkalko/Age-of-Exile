package com.robertx22.mine_and_slash.event_hooks.entity;

import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import nerdhub.cardinal.components.api.event.TrackingStartCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnTrackEntity implements TrackingStartCallback {

    @Override
    public void onPlayerStartTracking(ServerPlayerEntity serverPlayerEntity, Entity entity) {

        try {
            if (entity instanceof LivingEntity) {

                if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                    return;
                }

                if (entity.isPartOf(serverPlayerEntity) == false) {

                    Packets.sendToClient(serverPlayerEntity,
                        Unit.getUpdatePacketFor((LivingEntity) entity, Load.Unit(entity))
                    );

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

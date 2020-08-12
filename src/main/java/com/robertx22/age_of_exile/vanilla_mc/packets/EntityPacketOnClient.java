package com.robertx22.age_of_exile.vanilla_mc.packets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class EntityPacketOnClient {
    @Environment(EnvType.CLIENT)
    public static void onPacket(PacketContext context, PacketByteBuf byteBuf) {
        EntityType<?> type = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
        UUID entityUUID = byteBuf.readUuid();
        int entityID = byteBuf.readVarInt();
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float pitch = (byteBuf.readByte() * 360) / 256.0F;
        float yaw = (byteBuf.readByte() * 360) / 256.0F;
        context.getTaskQueue()
            .execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                Entity entity = type.create(world);
                if (entity != null) {
                    entity.updatePosition(x, y, z);
                    entity.updateTrackedPosition(x, y, z);
                    entity.pitch = pitch;
                    entity.yaw = yaw;
                    entity.setEntityId(entityID);
                    entity.setUuid(entityUUID);
                    //entity.setVelocity(velocityX, velocityY, velocityZ);
                    world.addEntity(entityID, entity);
                }
            });
    }
}

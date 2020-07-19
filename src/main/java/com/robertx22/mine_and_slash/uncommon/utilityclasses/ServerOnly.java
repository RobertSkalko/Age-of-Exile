package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class ServerOnly {

    public static Entity getEntityByUUID(World world, UUID id) {

        if (world instanceof ServerWorld) {
            return ((ServerWorld) world).getEntity(id);
        }

        return null;

    }

    public static void sendUpdate(BlockPos pos, PlayerEntity player) {

        BlockEntity tile = player.world.getBlockEntity(pos);

        if (tile != null) {

            BlockEntityUpdateS2CPacket supdatetileentitypacket = tile.toUpdatePacket();
            if (supdatetileentitypacket != null) {
                ((ServerPlayerEntity) player).networkHandler.sendPacket(supdatetileentitypacket);
            }
        }

    }
}

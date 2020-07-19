package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.Optional;

public class PlayerUtils {

    public static void giveItem(ItemStack stack, PlayerEntity player) {
        if (player.giveItemStack(stack) == false) {
            player.dropStack(stack, 1F);
        }
        player.inventory.markDirty();
    }

    public static PlayerEntity nearestPlayer(ServerWorld world, LivingEntity entity) {
        return nearestPlayer(world, entity.getPos());
    }

    public static PlayerEntity nearestPlayer(ServerWorld world, Vec3d pos) {

        Optional<ServerPlayerEntity> player = world.getPlayers()
            .stream()
            .min(Comparator.comparingDouble(x -> x.squaredDistanceTo(pos)));

        if (player.isPresent()) {
            return player.get();
        }

        return null;

    }

}

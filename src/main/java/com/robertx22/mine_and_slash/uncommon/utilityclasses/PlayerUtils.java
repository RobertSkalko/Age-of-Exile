package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.Optional;

public class PlayerUtils {

    public static BlockPos getBedLocation(PlayerEntity player) {

        BlockPos pos = null;

        if (pos == null) {
            Optional<BlockPos> opt = player.getSleepingPosition();
            if (opt.isPresent()) {
                pos = opt.get();
            }
        }
        if (pos == null) {
            pos = player.getBedLocation(player.world.getDimension()
                .getType());
        }
        if (pos == null) {
            pos = player.getSpawnPosition();
        }

        return pos;
    }

    public static void giveItem(ItemStack stack, PlayerEntity player) {
        if (player.giveItemStack(stack) == false) {
            player.dropStack(stack, 1F);
        }
        player.inventory.markDirty();
    }

    @Nullable
    public static PlayerEntity nearestPlayer(ServerWorld world, LivingEntity entity) {
        return nearestPlayer(world, entity.getPos());
    }

    @Nullable
    public static PlayerEntity nearestPlayer(ServerWorld world, Vec3d pos) {

        Optional<ServerPlayerEntity> player = world.getPlayers()
            .stream()
            .min(Comparator.comparingDouble(x -> x.squaredDistanceTo(pos)));

        if (player.isPresent()) {
            return player.get();
        }

        return null;

    }

    public static CompoundTag getPersistentNBT(PlayerEntity player) {

        CompoundTag nbt = null;

        try {

            Tag basenbt = player.getPersistentData();

            if (basenbt != null) {
                nbt = (CompoundTag) basenbt;
            }
            if (nbt == null) {
                nbt = new CompoundTag();
            }

        } catch (Exception e) {
            nbt = new CompoundTag();
            e.printStackTrace();
        }

        return nbt;

    }

    public static void setPestistentNBT(PlayerEntity player, CompoundTag nbt) {

        player.getPersistentData()
            .put(PlayerEntity.PERSISTED_NBT_TAG, nbt);
    }

}

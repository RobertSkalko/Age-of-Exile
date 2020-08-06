package com.robertx22.library_of_exile.utils;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundUtils {

    public static void playSound(Entity entity, SoundEvent sound, float volume, float pitch) {
        //this should be universal
        entity.world.playSound(null, entity.getBlockPos(), sound, SoundCategory.PLAYERS, volume, pitch);

    }

    public static void playSound(World world, BlockPos pos, SoundEvent sound, float volume, float pitch) {
        //this should be universal
        world.playSound(null, pos, sound, SoundCategory.PLAYERS, volume, pitch);

    }

    public static void ding(World world, BlockPos pos) {
        SoundUtils.playSound(world, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }

}

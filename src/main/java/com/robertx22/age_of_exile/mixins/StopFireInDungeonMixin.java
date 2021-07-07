package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(FireBlock.class)
public class StopFireInDungeonMixin {
    @Inject(method = "scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        try {
            if (WorldUtils.isMapWorldClass(world)) {
                ci.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        try {
            if (WorldUtils.isDungeonWorld(world)) {
                if (world.getBlockState(pos.down())
                    .getBlock() != Blocks.NETHERRACK) {
                    ci.setReturnValue(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

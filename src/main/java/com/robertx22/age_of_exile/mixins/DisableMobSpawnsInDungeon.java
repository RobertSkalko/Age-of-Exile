package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SpawnRestriction.class)
public class DisableMobSpawnsInDungeon {

    @Inject(method = "canSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static <T extends Entity> void disableCanSpawn(EntityType<T> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> ci) {
        if (WorldUtils.isDungeonWorld(serverWorldAccess)) {
            if (spawnReason == SpawnReason.NATURAL) {
                ci.setReturnValue(false);
            }
        }
    }

}

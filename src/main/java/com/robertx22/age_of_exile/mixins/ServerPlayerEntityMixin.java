package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    // keep saved inventory items after clicking respawn button
    @Inject(method = "copyFrom", at = @At(value = "HEAD"))
    private void dontDropGear(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        try {
            if (ModConfig.get().Server.SAVE_GEAR_AND_HOTBAR_ON_DEATH) {

                if (oldPlayer.world.getGameRules()
                    .getBoolean(GameRules.KEEP_INVENTORY)) {
                    return; // no need
                }

                ServerPlayerEntity p = (ServerPlayerEntity) (Object) this;
                p.inventory.clone(oldPlayer.inventory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

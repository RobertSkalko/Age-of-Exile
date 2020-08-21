package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "onPlayerConnect", at = @At(value = "RETURN"))
    public void hook(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        try {
            OnLogin.onLoad(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

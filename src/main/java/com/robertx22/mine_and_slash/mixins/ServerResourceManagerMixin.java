package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.mmorpg.DataGeneration;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public abstract class ServerResourceManagerMixin {

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void my$onConstruct(CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {
        ServerResourceManager m = (ServerResourceManager) (Object) this;
        ReloadableResourceManager manager = (ReloadableResourceManager) m.getResourceManager();
        DataGeneration.registerLoaders(manager);
    }
}

package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.PlayerTeleStateAccessor;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityAccessor implements PlayerTeleStateAccessor {

    @Accessor(value = "inTeleportationState")
    @Override
    public abstract void setIsInTeleportationState(boolean bool);

}

package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.PlayerTeleStateAccessor;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityAccessor implements PlayerTeleStateAccessor {

    @Accessor(value = "isChangingDimension")
    @Override
    public abstract void setawaitingTeleport(boolean bool);

}

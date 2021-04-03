package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "HEAD"))
    public void hook(Entity en, CallbackInfo ci) {
        try {
            PlayerEntity player = (PlayerEntity) (Object) this;
            Load.spells(player)
                .getCastingData()
                .onAction(player, PlayerAction.MELEE_ATTACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.robertx22.age_of_exile.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public class ExplosionMixin {
    // dont dmg entities or items, just destroy blocks
    @Redirect(method = "collectBlocksAndDamageEntities()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isImmuneToExplosion()Z"))
    public boolean hook(Entity entity) {
        return true;
    }
}

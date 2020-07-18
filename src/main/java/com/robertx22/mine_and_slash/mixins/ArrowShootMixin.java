package com.robertx22.mine_and_slash.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public abstract class ArrowShootMixin {

    @Inject(method = "setProperties(Lnet/minecraft/entity/Entity;FFFFF)V", at = @At("HEAD"))
    public void myOnShoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy, CallbackInfo ci) {
        ProjectileEntity arrow = (ProjectileEntity) (Object) this;

        float multi = MathHelper.clamp(velocity / 3, 0, 1); // by default it's multiplied by 3 so i need to divide it
// TODO TODO TODO
        //  arrow
        //   .getPersistentData()
        //     .putFloat(DamageEffect.ARROW_DMG_MULTI_TAG, multi);

    }

}



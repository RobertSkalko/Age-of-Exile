package com.robertx22.mine_and_slash.mixins;

import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ProjectileEntity.class)
public abstract class ArrowShootMixin {
/*
    @Inject(method = "setProperties(Lnet/minecraft/entity/Entity;FFFFF)V", at = @At("HEAD"))
    public void myOnShoot(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ, CallbackInfo ci) {
        ProjectileEntity arrow = (ProjectileEntity) (Object) this;

        float multi = MathHelper.clamp(modifierXYZ / 3, 0, 1); // by default it's multiplied by 3 so i need to divide it

        arrow.



// TODO TODO TODO
        //  arrow
        //   .getPersistentData()
        //     .putFloat(DamageEffect.ARROW_DMG_MULTI_TAG, multi);

    }
    */

}



package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.ProjectileEntityDuck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public class ArrowShootMixin implements ProjectileEntityDuck {

    public float dmg$multi = 0;

    @Inject(method = "setProperties(Lnet/minecraft/entity/Entity;FFFFF)V", at = @At("HEAD"))
    public void myOnShoot(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ, CallbackInfo ci) {
        ProjectileEntity arrow = (ProjectileEntity) (Object) this;
        //System.out.println(modifierZ);
        dmg$multi = MathHelper.clamp(modifierZ / 3F, 0, 1); // by default it's multiplied by 3 so i need to divide it
    }

    @Override
    public float my$getDmgMulti() {
        return dmg$multi;
    }
}



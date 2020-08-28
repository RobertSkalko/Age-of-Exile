package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.DestroyedOnLandingDuck;
import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin implements DestroyedOnLandingDuck {

    @Accessor(value = "destroyedOnLanding")
    @Override
    public abstract void setDestroyedOnLanding(boolean bool);

}

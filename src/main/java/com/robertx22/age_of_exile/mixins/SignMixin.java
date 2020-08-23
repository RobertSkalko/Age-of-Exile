package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.SignDuck;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SignBlockEntity.class)
public abstract class SignMixin implements SignDuck {

    @Accessor
    @Override
    public abstract Text[] getText();
}

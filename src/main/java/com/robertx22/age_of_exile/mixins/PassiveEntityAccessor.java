package com.robertx22.age_of_exile.mixins;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.PassiveEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PassiveEntity.class)
public interface PassiveEntityAccessor {
    @Accessor
    static TrackedData<Boolean> getCHILD() {
        throw new UnsupportedOperationException();
    }
}

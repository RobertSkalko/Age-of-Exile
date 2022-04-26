package com.robertx22.age_of_exile.mixins;

import net.minecraft.client.renderer.model.ItemOverride;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemOverride.class)
public interface ItemOverrideAccessor {
    @Accessor
    ResourceLocation getModel();
}

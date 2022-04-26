package com.robertx22.age_of_exile.mixins;

import net.minecraft.client.renderer.model.ItemOverride;
import net.minecraft.client.renderer.model.ItemOverrideList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ItemOverrideList.class)
public interface ItemOverrideListAccessor {
    @Accessor
    List<ItemOverride> getOverrides();
}

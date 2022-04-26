package com.robertx22.age_of_exile.mixins;

import net.minecraft.block.SkullBlock;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SkullTileEntityRenderer.class)
public interface SkullTileEntityRendererAccessor {
    @Accessor
    static Map<SkullBlock.ISkullType, GenericHeadModel> getMODEL_BY_TYPE() {
        throw new UnsupportedOperationException();
    }
}

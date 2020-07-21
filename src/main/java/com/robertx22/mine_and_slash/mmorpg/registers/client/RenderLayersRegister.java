package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class RenderLayersRegister {
    public static void setup() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GEAR_SALVAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GEAR_MODIFY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GEAR_REPAIR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.MAGMA_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.THORN_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.HOLY_FLOWER, RenderLayer.getCutout());
    }
}

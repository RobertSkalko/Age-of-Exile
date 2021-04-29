package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class RenderLayersRegister {
    public static void setup() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GEAR_SALVAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GEAR_MODIFY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GEAR_REPAIR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.SOCKET_STATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.SPAWNER, RenderLayer.getCutout());

        ModRegistry.BLOCKS.FARMING_PLANTS.values()
            .forEach(x -> {
                BlockRenderLayerMap.INSTANCE.putBlock(x, RenderLayer.getCutout());
            });

    }
}

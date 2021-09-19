package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class RenderLayersRegister {
    public static void setup() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.PORTAL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.BLACK_HOLE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.TELEPORTER, RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.MANA_PLANT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.LIFE_PLANT, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.BLUE_TOTEM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GUARD_TOTEM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.TRAP, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GREEN_TOTEM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GLYPH, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.GEAR_SALVAGE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.RUNEWORD_STATION, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BLOCKS.SPAWNER, RenderType.cutout());

        ModRegistry.BLOCKS.FARMING_PLANTS.values()
            .forEach(x -> {
                BlockRenderLayerMap.INSTANCE.putBlock(x, RenderType.cutout());
            });

    }
}

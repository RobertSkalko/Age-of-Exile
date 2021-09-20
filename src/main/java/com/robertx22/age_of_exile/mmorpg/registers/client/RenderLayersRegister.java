package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class RenderLayersRegister {
    public static void setup() {


        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.PORTAL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.BLACK_HOLE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.TELEPORTER.get(), RenderType.translucent());

        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.MANA_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.LIFE_PLANT.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.BLUE_TOTEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.GUARD_TOTEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.TRAP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.GREEN_TOTEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.GLYPH.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.GEAR_SALVAGE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.RUNEWORD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModRegistry.BLOCKS.SPAWNER.get(), RenderType.cutout());

        ModRegistry.BLOCKS.FARMING_PLANTS.values()
                .forEach(x -> {
                    RenderTypeLookup.setRenderLayer(x.get(), RenderType.cutout());
                });

    }
}

package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class RenderLayersRegister {
    public static void setup() {

        RenderTypeLookup.setRenderLayer(SlashBlocks.PORTAL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SlashBlocks.BLACK_HOLE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SlashBlocks.TELEPORTER.get(), RenderType.translucent());

        RenderTypeLookup.setRenderLayer(SlashBlocks.MANA_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SlashBlocks.LIFE_PLANT.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(SlashBlocks.BLUE_TOTEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SlashBlocks.GUARD_TOTEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SlashBlocks.TRAP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SlashBlocks.GREEN_TOTEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SlashBlocks.GLYPH.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(SlashBlocks.GEAR_SALVAGE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SlashBlocks.RUNEWORD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SlashBlocks.SPAWNER.get(), RenderType.cutout());

        SlashBlocks.FARMING_PLANTS.values()
            .forEach(x -> {
                RenderTypeLookup.setRenderLayer(x.get(), RenderType.cutout());
            });

    }
}

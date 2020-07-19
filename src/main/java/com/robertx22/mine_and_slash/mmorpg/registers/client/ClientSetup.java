package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.a_libraries.curios.CurioClientSetup;
import com.robertx22.mine_and_slash.gui.overlays.bar_overlays.types.VanillaOverlay;
import com.robertx22.mine_and_slash.gui.overlays.mob_bar.MobBarScreen;
import com.robertx22.mine_and_slash.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.mine_and_slash.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraftforge.common.MinecraftForge;

public class ClientSetup {

    public static void setup() {

        RenderLayers.setRenderLayer(ModBlocks.GEAR_MODIFY.get(), RenderLayer.getCutout());
        RenderLayers.setRenderLayer(ModBlocks.GEAR_SALVAGE.get(), RenderLayer.getCutout());//cutout
        RenderLayers.setRenderLayer(ModBlocks.GEAR_REPAIR.get(), RenderLayer.getCutout());//cutout

        RenderLayers.setRenderLayer(ModBlocks.MAGMA_FLOWER.get(), RenderLayer.getCutout());
        RenderLayers.setRenderLayer(ModBlocks.THORN_BUSH.get(), RenderLayer.getCutout());
        RenderLayers.setRenderLayer(ModBlocks.HOLY_FLOWER.get(), RenderLayer.getCutout());

        CurioClientSetup.setup();

        MinecraftForge.EVENT_BUS.register(new VanillaOverlay(MinecraftClient.getInstance()));
        MinecraftForge.EVENT_BUS.register(new MobBarScreen(MinecraftClient.getInstance()));
        MinecraftForge.EVENT_BUS.register(new SpellCastBarOverlay());
        MinecraftForge.EVENT_BUS.register(new SpellHotbarOverlay());

        KeybindsRegister.register();
        ContainerGuiRegisters.reg();
        RenderRegister.regRenders();
    }
}

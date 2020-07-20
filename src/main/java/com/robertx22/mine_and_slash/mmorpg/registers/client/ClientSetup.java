package com.robertx22.mine_and_slash.mmorpg.registers.client;

public class ClientSetup {

    public static void setup() {

            /*
            RenderLayers.setRenderLayer(ModBlocks.GEAR_MODIFY.get(), RenderLayer.getCutout());
            RenderLayers.setRenderLayer(ModBlocks.GEAR_SALVAGE.get(), RenderLayer.getCutout());//cutout
            RenderLayers.setRenderLayer(ModBlocks.GEAR_REPAIR.get(), RenderLayer.getCutout());//cutout

            RenderLayers.setRenderLayer(ModBlocks.MAGMA_FLOWER.get(), RenderLayer.getCutout());
            RenderLayers.setRenderLayer(ModBlocks.THORN_BUSH.get(), RenderLayer.getCutout());
            RenderLayers.setRenderLayer(ModBlocks.HOLY_FLOWER.get(), RenderLayer.getCutout());

             */

            /*
            MinecraftForge.EVENT_BUS.register(new VanillaOverlay(MinecraftClient.getInstance()));
            MinecraftForge.EVENT_BUS.register(new MobBarScreen(MinecraftClient.getInstance()));
            MinecraftForge.EVENT_BUS.register(new SpellCastBarOverlay());
            MinecraftForge.EVENT_BUS.register(new SpellHotbarOverlay());
             */

        // TODO

        ContainerGuiRegisters.reg();
        RenderRegister.regRenders();
    }
}

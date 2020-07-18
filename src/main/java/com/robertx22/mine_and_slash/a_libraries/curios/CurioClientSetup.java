package com.robertx22.mine_and_slash.a_libraries.curios;


import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CurioClientSetup {
    public static final String CURIOS = "curios";

    public static void setup(final FMLClientSetupEvent event) {

    }

    private static void send(String id, Object msg) {
        InterModComms.sendTo(CURIOS, id, () -> msg);
    }

    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre evt) {

    }

}

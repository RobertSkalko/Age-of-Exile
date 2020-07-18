package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.MySpriteRenderer;
import com.robertx22.mine_and_slash.database.data.spells.entities.special.RangerArrowRenderer;
import com.robertx22.mine_and_slash.database.data.spells.entities.trident.HolyTridentRenderer;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ref.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderRegister {

    //@SubscribeEvent
    public static void regRenders(/*ModelRegistryEvent evt*/) {

        for (EntityType type : ModRegistry.ENTITIES.ENTITY_THAT_USE_ITEM_RENDERS) {
            RenderingRegistry.registerEntityRenderingHandler(type, newRenFac());
        }

        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.ENTITIES.HOLY_SPEAR, HolyTridentRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.ENTITIES.THUNDER_SPEAR, TridentEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.ENTITIES.RANGER_ARROW, RangerArrowRenderer::new);

    }

    private static <T extends Entity> IRenderFactory<? super T> newRenFac() {
        return manager -> new MySpriteRenderer<>(manager, MinecraftClient.getInstance()
            .getItemRenderer());
    }
}

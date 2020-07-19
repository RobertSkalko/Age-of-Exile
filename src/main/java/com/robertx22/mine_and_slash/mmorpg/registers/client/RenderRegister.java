package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.MySpriteRenderer;
import com.robertx22.mine_and_slash.database.data.spells.entities.special.RangerArrowRenderer;
import com.robertx22.mine_and_slash.database.data.spells.entities.trident.HolyTridentRenderer;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.EntityType;

public class RenderRegister {

    public static void regRenders() {

        for (EntityType type : ModRegistry.ENTITIES.ENTITY_THAT_USE_ITEM_RENDERS) {
            EntityRendererRegistry.INSTANCE.register(type, (dispatcher, context) -> new MySpriteRenderer<>(dispatcher, MinecraftClient.getInstance()
                .getItemRenderer()));
        }

        EntityRendererRegistry.INSTANCE.register(ModRegistry.ENTITIES.HOLY_SPEAR, (dispatcher, context) -> new HolyTridentRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModRegistry.ENTITIES.THUNDER_SPEAR, (dispatcher, context) -> new TridentEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModRegistry.ENTITIES.RANGER_ARROW, (dispatcher, context) -> new RangerArrowRenderer(dispatcher));

    }
}


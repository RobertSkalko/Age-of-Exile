package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.renders.ModTridentRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.RangerArrowRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.entity.EntityType;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE;

public class RenderRegister {

    public static void regRenders() {

        for (EntityType type : ENTITIES.ENTITY_THAT_USE_ITEM_RENDERS) {
            INSTANCE.register(type, (d, ctx) -> new MySpriteRenderer<>(d, MinecraftClient.getInstance()
                .getItemRenderer()));
        }

        INSTANCE.register(ENTITIES.SIMPLE_ARROW, (d, ctx) -> new RangerArrowRenderer<>(d));
        INSTANCE.register(ENTITIES.SIMPLE_BONE_PROJECTILE, (d, ctx) -> new RangerArrowRenderer<>(d));
        INSTANCE.register(ENTITIES.SIMPLE_BLOCK_ENTITY, (d, ctx) -> new FallingBlockEntityRenderer(d));
        INSTANCE.register(ENTITIES.SIMPLE_TRIDENT, (d, ctx) -> new ModTridentRenderer(d));

    }
}


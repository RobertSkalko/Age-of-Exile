package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.special.RangerArrowRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.trident.HolyTridentRenderer;
import com.robertx22.age_of_exile.mobs.slimes.ModSlimeRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.EntityType;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class RenderRegister {

    public static void regRenders() {

        for (EntityType type : ENTITIES.ENTITY_THAT_USE_ITEM_RENDERS) {
            EntityRendererRegistry.INSTANCE.register(type, (dispatcher, context) -> new MySpriteRenderer<>(dispatcher, MinecraftClient.getInstance()
                .getItemRenderer()));
        }

        EntityRendererRegistry.INSTANCE.register(ENTITIES.HOLY_SPEAR, (dispatcher, context) -> new HolyTridentRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ENTITIES.THUNDER_SPEAR, (dispatcher, context) -> new TridentEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ENTITIES.RANGER_ARROW, (dispatcher, context) -> new RangerArrowRenderer(dispatcher));

        EntityRendererRegistry.INSTANCE.register(ENTITIES.ARCANE_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "arcane_slime.png"));
        EntityRendererRegistry.INSTANCE.register(ENTITIES.FIRE_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "fire_slime.png"));
        EntityRendererRegistry.INSTANCE.register(ENTITIES.WATER_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "water_slime.png"));
        EntityRendererRegistry.INSTANCE.register(ENTITIES.THUNDER_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "thunder_slime.png"));
        EntityRendererRegistry.INSTANCE.register(ENTITIES.NATURE_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "nature_slime.png"));

    }
}


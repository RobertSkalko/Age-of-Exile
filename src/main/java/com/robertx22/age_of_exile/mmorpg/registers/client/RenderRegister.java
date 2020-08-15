package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.special.RangerArrowRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.trident.HolyTridentRenderer;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mobs.ModSlimeRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class RenderRegister {

    public static void regRenders() {

        for (EntityType type : ModRegistry.ENTITIES.ENTITY_THAT_USE_ITEM_RENDERS) {
            EntityRendererRegistry.INSTANCE.register(type, (dispatcher, context) -> new MySpriteRenderer<>(dispatcher, MinecraftClient.getInstance()
                .getItemRenderer()));
        }

        EntityRendererRegistry.INSTANCE.register(ModRegistry.ENTITIES.HOLY_SPEAR, (dispatcher, context) -> new HolyTridentRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModRegistry.ENTITIES.THUNDER_SPEAR, (dispatcher, context) -> new TridentEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(ModRegistry.ENTITIES.RANGER_ARROW, (dispatcher, context) -> new RangerArrowRenderer(dispatcher));

        EntityRendererRegistry.INSTANCE.register(ModRegistry.ENTITIES.ARCANE_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, new Identifier(Ref.MODID, "textures/entity/arcane_slime.png")));

    }
}


package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.renders.ModTridentRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.RangerArrowRenderer;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderRegister {

    public static void regRenders() {

        for (EntityType type : SlashEntities.ENTITY_THAT_USE_ITEM_RENDERS) {

            RenderingRegistry.registerEntityRenderingHandler(type, (d) -> new MySpriteRenderer<>(d, Minecraft.getInstance()
                .getItemRenderer()));

        }

        RenderingRegistry.registerEntityRenderingHandler(SlashEntities.SIMPLE_ARROW.get(), m -> new RangerArrowRenderer(m));
        RenderingRegistry.registerEntityRenderingHandler(SlashEntities.SIMPLE_BLOCK_ENTITY.get(), m -> new FallingBlockRenderer(m));
        RenderingRegistry.registerEntityRenderingHandler(SlashEntities.SIMPLE_TRIDENT.get(), m -> new ModTridentRenderer(m));

    }
}


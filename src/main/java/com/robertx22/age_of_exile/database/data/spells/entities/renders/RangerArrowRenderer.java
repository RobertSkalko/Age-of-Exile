package com.robertx22.age_of_exile.database.data.spells.entities.renders;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class RangerArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<T> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public RangerArrowRenderer(EntityRenderDispatcher manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTexture(T en) {
        return RES_ARROW;
    }

}
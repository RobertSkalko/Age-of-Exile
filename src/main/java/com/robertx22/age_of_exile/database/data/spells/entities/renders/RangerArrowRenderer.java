package com.robertx22.age_of_exile.database.data.spells.entities.renders;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;

public class RangerArrowRenderer<T extends AbstractArrowEntity> extends ArrowRenderer<T> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public RangerArrowRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(T en) {
        return RES_ARROW;
    }

}
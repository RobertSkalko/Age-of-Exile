package com.robertx22.age_of_exile.database.data.spells.entities.renders;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;

public class RangerArrowRenderer<T extends PersistentProjectileEntity> extends ProjectileEntityRenderer<T> {
    public static final Identifier RES_ARROW = new Identifier("textures/entity/projectiles/arrow.png");
    //public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");

    public RangerArrowRenderer(EntityRenderDispatcher manager) {
        super(manager);
    }

    @Override
    public Identifier getTexture(T en) {
        return RES_ARROW;
    }

}
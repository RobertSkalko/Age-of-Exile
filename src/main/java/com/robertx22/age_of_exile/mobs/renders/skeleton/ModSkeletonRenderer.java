package com.robertx22.age_of_exile.mobs.renders.skeleton;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class ModSkeletonRenderer extends SkeletonEntityRenderer {
    private Identifier TEXTURE = new Identifier("textures/entity/skeleton/stray.png");

    public ModSkeletonRenderer(EntityRenderDispatcher entityRenderDispatcher, String id, String overlay) {
        super(entityRenderDispatcher);
        this.addFeature(new ModSkeletonOverlayRenderer<>(this, overlay));
        this.TEXTURE = new Identifier(Ref.MODID, "textures/entity/skeleton/" + id);
    }

    @Override
    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return TEXTURE;
    }
}

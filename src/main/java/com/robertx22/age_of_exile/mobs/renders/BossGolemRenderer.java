package com.robertx22.age_of_exile.mobs.renders;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

public class BossGolemRenderer extends MobEntityRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier(Ref.MODID, "textures/entity/bosses/golem.png");

    public BossGolemRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new IronGolemEntityModel(), 0.7F);
    }

    @Override
    public Identifier getTexture(IronGolemEntity ironGolemEntity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(IronGolemEntity ironGolemEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(ironGolemEntity, matrixStack, f, g, h);
        if ((double) ironGolemEntity.limbDistance >= 0.01D) {
            float i = 13.0F;
            float j = ironGolemEntity.limbAngle - ironGolemEntity.limbDistance * (1.0F - h) + 6.0F;
            float k = (Math.abs(j % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.5F * k));
        }
    }
}

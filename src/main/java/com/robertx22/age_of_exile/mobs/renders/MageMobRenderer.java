package com.robertx22.age_of_exile.mobs.renders;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mobs.mages.BaseMage;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// witch render doesnt have arms, and cant have them
public class MageMobRenderer extends MobEntityRenderer<BaseMage, WitchEntityModel<BaseMage>> {
    private Identifier TEXTURE;

    public MageMobRenderer(EntityRenderDispatcher entityRenderDispatcher, String tex) {
        super(entityRenderDispatcher, new WitchEntityModel(0.0F), 0.5F);
        this.TEXTURE = new Identifier(Ref.MODID, "textures/entity/mages/" + tex);
    }

    @Override
    public void render(BaseMage witchEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        (this.model).setLiftingNose(!witchEntity.getMainHandStack()
            .isEmpty());
        super.render(witchEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(BaseMage witchEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(BaseMage witchEntity, MatrixStack matrixStack, float f) {
        float g = 0.9375F;
        matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}

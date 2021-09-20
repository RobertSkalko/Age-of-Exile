package com.robertx22.age_of_exile.database.data.spells.entities.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ModTridentRenderer extends EntityRenderer<ProjectileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/trident.png");
    private final TridentModel model = new TridentModel();

    public ModTridentRenderer(EntityRendererManager entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(ProjectileEntity tridentEntity, float f, float g, MatrixStack matrixStack, IRenderTypeBuffer vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(g, tridentEntity.yRotO, tridentEntity.yRot) - 90.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(g, tridentEntity.xRotO, tridentEntity.xRot) + 90.0F));
        IVertexBuilder vertexConsumer = ItemRenderer.getFoilBufferDirect(vertexConsumerProvider, this.model.renderType(this.getTextureLocation(tridentEntity)), false, false);
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
        super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(ProjectileEntity tridentEntity) {
        return TEXTURE;
    }

}

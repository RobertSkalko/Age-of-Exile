package com.robertx22.age_of_exile.database.data.spells.entities.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class MySpriteRenderer<T extends Entity & IMyRenderAsItem> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public MySpriteRenderer(EntityRendererManager dispatcher, ItemRenderer itemRenderer, float scale, boolean lit) {
        super(dispatcher);
        this.itemRenderer = itemRenderer;
        this.scale = scale;
        this.lit = lit;
    }

    public MySpriteRenderer(EntityRendererManager dispatcher, ItemRenderer itemRenderer) {
        this(dispatcher, itemRenderer, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos blockPos) {
        return this.lit ? 15 : super.getBlockLightLevel(entity, blockPos);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, IRenderTypeBuffer vertexConsumers, int light) {
        if (entity.tickCount >= 3) {
            matrices.pushPose();

            if (entity.getScale() != 1F) {
                float s = entity.getScale();
                matrices.scale(s, s, s);
            } else {
                matrices.scale(this.scale, this.scale, this.scale);
            }
            matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
            matrices.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderStatic(((IMyRenderAsItem) entity).getItem(), ItemCameraTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, matrices, vertexConsumers);
            matrices.popPose();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        }
    }

    public ResourceLocation getTextureLocation(Entity entity) {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}

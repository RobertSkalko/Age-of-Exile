package com.robertx22.mine_and_slash.database.data.spells.entities.bases;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public abstract class BaseProjectileRenderer<T extends Entity> extends EntityRenderer<T> {

    private Model model;

    public BaseProjectileRenderer(EntityRenderDispatcher manager, Model model) {
        super(manager);
        this.model = model;
    }

    @Override
    public void render(T en, float f1, float f2, MatrixStack matrix, VertexConsumerProvider buffer, int int1) {

        matrix.push();
        matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(f2, en.prevYaw, en.yaw) - 90.0F));
        matrix.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(f2, en.prevPitch, en.pitch) + 90.0F));
        VertexConsumer builder = ItemRenderer.getArmorVertexConsumer(buffer, this.model.getLayer(this.getTexture(en)),
            false, true
        );

        this.model.render(matrix, builder, int1, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrix.pop();
        super.render(en, f1, f2, matrix, buffer, int1);
    }

}

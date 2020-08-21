package com.robertx22.age_of_exile.mobs.renders.skeleton;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class ModSkeletonOverlayRenderer<T extends MobEntity & RangedAttackMob, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private Identifier TEXTURE = new Identifier("textures/entity/skeleton/stray_overlay.png");
    private final SkeletonEntityModel<T> model = new SkeletonEntityModel(0.25F, true);

    public ModSkeletonOverlayRenderer(FeatureRendererContext<T, M> featureRendererContext, String id) {
        super(featureRendererContext);
        this.TEXTURE = new Identifier(Ref.MODID, "textures/entity/skeleton/" + id);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T mobEntity, float f, float g, float h, float j, float k, float l) {
        render(this.getContextModel(), this.model, TEXTURE, matrixStack, vertexConsumerProvider, i, mobEntity, f, g, j, k, l, h, 1.0F, 1.0F, 1.0F);
    }
}


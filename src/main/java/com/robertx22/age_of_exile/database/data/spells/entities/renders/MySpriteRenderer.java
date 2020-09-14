package com.robertx22.age_of_exile.database.data.spells.entities.renders;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class MySpriteRenderer<T extends Entity> extends EntityRenderer<T> {
    private final net.minecraft.client.render.item.ItemRenderer itemRenderer;
    private final float scale;
    private final boolean field_229126_f_;

    public MySpriteRenderer(EntityRenderDispatcher p_i226035_1_, net.minecraft.client.render.item.ItemRenderer p_i226035_2_,
                            float p_i226035_3_, boolean p_i226035_4_) {
        super(p_i226035_1_);
        this.itemRenderer = p_i226035_2_;
        this.scale = p_i226035_3_;
        this.field_229126_f_ = p_i226035_4_;
    }

    public MySpriteRenderer(EntityRenderDispatcher p_i50957_1_, net.minecraft.client.render.item.ItemRenderer p_i50957_2_) {
        this(p_i50957_1_, p_i50957_2_, 1.0F, false);
    }

    public void render(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_,
                       VertexConsumerProvider p_225623_5_, int p_225623_6_) {
        p_225623_4_.push();
        p_225623_4_.scale(this.scale, this.scale, this.scale);
        p_225623_4_.multiply(this.dispatcher.getRotation());
        p_225623_4_.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        this.itemRenderer.renderItem(((IMyRenderAsItem) p_225623_1_).getItem(),
            ModelTransformation.Mode.GROUND, p_225623_6_,
            OverlayTexture.DEFAULT_UV, p_225623_4_, p_225623_5_
        );
        p_225623_4_.pop();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    public Identifier getTexture(Entity p_110775_1_) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEX;
    }
}
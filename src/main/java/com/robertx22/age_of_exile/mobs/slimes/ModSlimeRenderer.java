package com.robertx22.age_of_exile.mobs.slimes;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.MagmaCubeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ModSlimeRenderer extends MobEntityRenderer<MagmaCubeEntity, MagmaCubeEntityModel<MagmaCubeEntity>> {
    private Identifier TEXTURE = new Identifier(Ref.MODID, "textures/entity/slime/arcane_slime.png");

    public ModSlimeRenderer(EntityRenderDispatcher entityRenderDispatcher, String tex) {
        super(entityRenderDispatcher, new MagmaCubeEntityModel(), 0.25F);
        this.TEXTURE = new Identifier(Ref.MODID, "textures/entity/slime/" + tex);
    }

    @Override
    protected int getBlockLight(MagmaCubeEntity magmaCubeEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public Identifier getTexture(MagmaCubeEntity magmaCubeEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(MagmaCubeEntity magmaCubeEntity, MatrixStack matrixStack, float f) {
        int i = magmaCubeEntity.getSize();
        float g = MathHelper.lerp(f, magmaCubeEntity.lastStretch, magmaCubeEntity.stretch) / ((float) i * 0.5F + 1.0F);
        float h = 1.0F / (g + 1.0F);
        matrixStack.scale(h * (float) i, 1.0F / h * (float) i, h * (float) i);
    }
}

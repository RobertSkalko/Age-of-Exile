package com.robertx22.age_of_exile.mobs.renders;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.Identifier;

public class ModSpiderRenderer extends MobEntityRenderer<SpiderEntity, SpiderEntityModel<SpiderEntity>> {
    private Identifier TEXTURE = new Identifier(Ref.MODID, "textures/entity/slime/arcane_slime.png");

    public ModSpiderRenderer(EntityRenderDispatcher entityRenderDispatcher, String tex) {
        super(entityRenderDispatcher, new SpiderEntityModel<SpiderEntity>(), 0.25F);
        this.TEXTURE = new Identifier(Ref.MODID, "textures/entity/spider/" + tex);
        this.addFeature(new SpiderEyesFeatureRenderer(this));
    }

    @Override
    protected float getLyingAngle(SpiderEntity spiderEntity) {
        return 180.0F;
    }

    @Override
    public Identifier getTexture(SpiderEntity spiderEntity) {
        return TEXTURE;
    }
}

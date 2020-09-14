package com.robertx22.age_of_exile.mobs.renders;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mobs.chickens.ModChicken;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ModChickenRenderer extends MobEntityRenderer<ModChicken, ChickenEntityModel<ModChicken>> {
    private Identifier TEXTURE = new Identifier("textures/entity/chicken.png");

    public ModChickenRenderer(EntityRenderDispatcher entityRenderDispatcher, String id) {
        super(entityRenderDispatcher, new ChickenEntityModel(), 0.3F);
        this.TEXTURE = new Identifier(Ref.MODID, "textures/entity/chicken/" + id);
    }

    @Override
    public Identifier getTexture(ModChicken BaseChicken) {
        return TEXTURE;
    }

    @Override
    protected float getAnimationProgress(ModChicken BaseChicken, float f) {
        float g = MathHelper.lerp(f, BaseChicken.prevFlapProgress, BaseChicken.flapProgress);
        float h = MathHelper.lerp(f, BaseChicken.prevMaxWingDeviation, BaseChicken.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0F) * h;
    }
}

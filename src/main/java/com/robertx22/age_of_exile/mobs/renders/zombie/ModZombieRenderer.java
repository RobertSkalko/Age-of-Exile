package com.robertx22.age_of_exile.mobs.renders.zombie;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mobs.zombies.BaseZombie;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ModZombieRenderer extends ZombieBaseEntityRenderer<BaseZombie, DrownedEntityModel<BaseZombie>> {
    private Identifier TEXTURE;

    public ModZombieRenderer(EntityRenderDispatcher entityRenderDispatcher, String id, String id2) {
        super(entityRenderDispatcher, new DrownedEntityModel(0.0F, 0.0F, 64, 64), new DrownedEntityModel(0.5F, true), new DrownedEntityModel(1.0F, true));
        this.TEXTURE = new Identifier(Ref.MODID, "textures/entity/zombie/" + id);
        this.addFeature(new DrownedLayerRenderer(this, id2));
    }

    @Override
    public Identifier getTexture(BaseZombie zombieEntity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(BaseZombie drownedEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(drownedEntity, matrixStack, f, g, h);
        float i = drownedEntity.getLeaningPitch(h);
        if (i > 0.0F) {
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(i, drownedEntity.pitch, -10.0F - drownedEntity.pitch)));
        }

    }

}

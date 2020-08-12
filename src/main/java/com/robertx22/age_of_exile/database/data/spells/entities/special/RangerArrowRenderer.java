package com.robertx22.age_of_exile.database.data.spells.entities.special;

import com.robertx22.age_of_exile.database.data.spells.entities.proj.RangerArrowEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class RangerArrowRenderer extends ProjectileEntityRenderer<RangerArrowEntity> {
    public static final Identifier RES_ARROW = new Identifier("textures/entity/projectiles/arrow.png");
    //public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");

    public RangerArrowRenderer(EntityRenderDispatcher manager) {
        super(manager);
    }

    @Override
    public Identifier getTexture(RangerArrowEntity en) {
        return RES_ARROW;
    }

}

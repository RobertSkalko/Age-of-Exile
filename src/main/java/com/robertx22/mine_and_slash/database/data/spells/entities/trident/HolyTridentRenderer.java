package com.robertx22.mine_and_slash.database.data.spells.entities.trident;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;

public class HolyTridentRenderer extends TridentEntityRenderer {

    public HolyTridentRenderer(EntityRenderDispatcher p_i48828_1_) {
        super(p_i48828_1_);
    }

    public static final Identifier TRIDENT = new Identifier(Ref.MODID, "textures/entity/holy_trident.png");

    @Override
    public Identifier getTexture(TridentEntity en) {
        return TEXTURE;
    }
}

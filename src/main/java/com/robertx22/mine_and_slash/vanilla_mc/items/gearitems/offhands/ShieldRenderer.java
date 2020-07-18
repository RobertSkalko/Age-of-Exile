package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.offhands;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.concurrent.Callable;

public class ShieldRenderer extends BuiltinModelItemRenderer implements Callable<BuiltinModelItemRenderer> {
    public final BuiltinModelItemRenderer instance;

    private final ShieldEntityModel modelShield = new ShieldEntityModel();

    public ShieldRenderer() {
        instance = this;
    }

    @Override
    public void render(ItemStack stack, MatrixStack mat, VertexConsumerProvider renderType, int light, int overlayLight) {
        Item item = stack.getItem();

        if (item instanceof NormalShield) {

            NormalShield shield = (NormalShield) item;

            mat.push();

            mat.scale(1F, -0.6F, -1.0F);

            SpriteIdentifier material = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, shield.resource);

            VertexConsumer builder = material.getSprite()
                .getTextureSpecificVertexConsumer(ItemRenderer.getArmorVertexConsumer(renderType, modelShield.getLayer(material.getAtlasId()), false, stack.hasEnchantmentGlint()));

            modelShield.method_23775()
                .render(mat, builder, light, overlayLight, 1.0F, 1.0F, 1.0F, 1.0F);

            modelShield.method_23774()
                .render(mat, builder, light, overlayLight, 1.0F, 1.0F, 1.0F, 1.0F);

            mat.pop();

        }

    }

    @Override
    public BuiltinModelItemRenderer call() throws Exception {
        return INSTANCE;
    }
}


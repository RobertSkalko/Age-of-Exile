package com.robertx22.age_of_exile.database.data.reforge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.robertx22.age_of_exile.mixins.SkullTileEntityRendererAccessor;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ReforgeModelRender extends ItemStackTileEntityRenderer {

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType gui, MatrixStack matrix, IRenderTypeBuffer buffer, int int1, int int2) {
        Item item = stack.getItem();

        if (item instanceof ReforgeItem) {
            if (stack.hasTag()) {
                Reforge reforge = ReforgeItem.getReforge(stack);
                if (reforge != null) {
                    renderSkull(reforge.id, 180.0F, 0.0F, matrix, buffer, int1);
                }
            }
        }
    }

    public static void renderSkull(String id, float float1, float float2, MatrixStack p_228879_5_, IRenderTypeBuffer buffer, int p_228879_7_) {
        GenericHeadModel genericheadmodel = SkullTileEntityRendererAccessor.getMODEL_BY_TYPE()
            .get(SkullBlock.Types.PLAYER);
        p_228879_5_.pushPose();

        p_228879_5_.translate(0.5D, 0.0D, 0.5D);

        p_228879_5_.scale(-1.0F, -1.0F, 1.0F);
        IVertexBuilder ivertexbuilder = buffer.getBuffer(getRenderType(id));
        genericheadmodel.setupAnim(float2, float1, 0.0F);
        genericheadmodel.renderToBuffer(p_228879_5_, ivertexbuilder, p_228879_7_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_228879_5_.popPose();
    }

    private static RenderType getRenderType(String id) {
        ResourceLocation loc = SlashRef.id("textures/items/reforge/" + id + ".png");
        return RenderType.entityCutoutNoCullZOffset(loc);

    }
}

package com.robertx22.age_of_exile.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.mixin_methods.RenderMobInfo;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MobNameTagMixin {


    @Shadow
    public abstract FontRenderer getFont();

    @Shadow
    @Final
    protected EntityRendererManager entityRenderDispatcher;


    // TODO this might be the wrong way to do it.
    @Inject(method = "render", at = @At(value = "HEAD"),
            cancellable = true)
    private void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, IRenderTypeBuffer vertexConsumers, int light, CallbackInfo ci) {
        if (entity instanceof LivingEntity) {

            RenderMobInfo.renderLivingEntityLabelIfPresent(getFont(), this.entityRenderDispatcher, (LivingEntity) entity,
                    matrices, vertexConsumers, light);
            ci.cancel();

        }
    }

}

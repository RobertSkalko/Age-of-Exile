package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.RenderMobInfo;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
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
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Shadow
    public abstract TextRenderer getFontRenderer();

    @Shadow
    public abstract EntityRenderDispatcher getRenderManager();

    @Inject(method = "render", at = @At(value = "HEAD"),
        cancellable = true)
    private void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (entity instanceof LivingEntity) {
            RenderMobInfo.renderLivingEntityLabelIfPresent(getFontRenderer(), dispatcher, (LivingEntity) entity,
                matrices, vertexConsumers, light);
            ci.cancel();

        }
    }

}

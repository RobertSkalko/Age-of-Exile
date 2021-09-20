package com.robertx22.age_of_exile.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticleRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "renderLevel(Lcom/mojang/blaze3d/matrix/MatrixStack;FJZLnet/minecraft/client/renderer/ActiveRenderInfo;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/util/math/vector/Matrix4f;)V", at = @At(value = "RETURN"))
    private void render(MatrixStack matrices, float tickDelta, long limitTime,
                        boolean renderBlockOutline, ActiveRenderInfo camera, GameRenderer gameRenderer,
                        LightTexture lightmapTextureManager, Matrix4f matrix, CallbackInfo info) {
        DamageParticleRenderer.renderParticles(matrices, camera);
    }
}

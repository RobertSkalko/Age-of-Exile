package com.robertx22.age_of_exile.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.math.Matrix4f;
import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticleRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void render(MatrixStack matrices, float tickDelta, long limitTime,
                        boolean renderBlockOutline, ActiveRenderInfo camera, GameRenderer gameRenderer,
                        LightTexture lightmapTextureManager, Matrix4f matrix, CallbackInfo info) {
        DamageParticleRenderer.renderParticles(matrices, camera);
    }
}

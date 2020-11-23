package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Set;

public class DamageParticleRenderer {

    public static Set<DamageParticle> PARTICLES = new HashSet<>();

    public static void renderParticles(MatrixStack matrix, Camera camera) {
        for (DamageParticle p : PARTICLES) {
            renderParticle(matrix, p, camera);
            p.tick();
        }

        PARTICLES.removeIf(x -> x.age > 50);
    }

    private static void renderParticle(MatrixStack matrix, DamageParticle particle, Camera camera) {
        float scaleToGui = 0.025f;

        MinecraftClient client = MinecraftClient.getInstance();
        float tickDelta = client.getTickDelta();

        double x = MathHelper.lerp((double) tickDelta, particle.xPrev, particle.x);
        double y = MathHelper.lerp((double) tickDelta, particle.yPrev, particle.y);
        double z = MathHelper.lerp((double) tickDelta, particle.zPrev, particle.z);

        Vec3d camPos = camera.getPos();
        double camX = camPos.x;
        double camY = camPos.y;
        double camZ = camPos.z;

        matrix.push();
        matrix.translate(x - camX, y - camY, z - camZ);
        matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-camera.getYaw()));
        matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
        matrix.scale(-scaleToGui, -scaleToGui, scaleToGui);

        RenderSystem.disableLighting();
        RenderSystem.enableDepthTest();
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE,
            GL11.GL_ZERO);
        RenderSystem.shadeModel(7425);

        drawDamageNumber(matrix, particle.element, particle.str, 0, 0, 10);

        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();

        matrix.pop();
    }

    public static void drawDamageNumber(MatrixStack matrix, Elements ele, String s, double x, double y,
                                        float width) {

        MinecraftClient minecraft = MinecraftClient.getInstance();
        int sw = minecraft.textRenderer.getWidth(s);
        minecraft.textRenderer.drawWithShadow(matrix, s, (int) (x + (width / 2) - sw), (int) y + 5, ele.format.getColorValue());
    }
}
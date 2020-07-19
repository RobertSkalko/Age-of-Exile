package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class DamageParticle extends Particle {

    protected String text;
    protected float scale = 0.7F;
    Elements element;
    public boolean grow = true;

    float locX;
    float locY;
    float locZ;

    boolean positionNeedsSetting = true;

    public DamageParticle(Elements element, String str, World world, double parX, double parY, double parZ,
                          double parMotionX, double parMotionY, double parMotionZ) {
        super((ClientWorld) world, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);

        gravityStrength = (float) ClientConfigs.INSTANCE.dmgParticleConfig.GRAVITY;

        scale = (float) ClientConfigs.INSTANCE.dmgParticleConfig.START_SIZE;

        this.maxAge = (int) ClientConfigs.INSTANCE.dmgParticleConfig.LIFESPAN;

        this.text = element.format + element.icon + Formatting.GRAY + str;
        this.element = element;
    }

    public void setupPosition(Camera info) {
        MinecraftClient mc = MinecraftClient.getInstance();

        float speed = (float) ClientConfigs.INSTANCE.dmgParticleConfig.SPEED;

        PlayerEntity p = mc.player;

        Vec3d view = info.getPos();

        locX = ((float) (this.prevPosX + (this.x - this.prevPosX) * x - view.getX())) * speed;
        locY = ((float) (this.prevPosY + (this.y - this.prevPosY) * y - view.getY())) * speed;
        locZ = ((float) (this.prevPosZ + (this.z - this.prevPosZ) * z - view.getZ())) * speed;

        positionNeedsSetting = false;

    }

    @Override
    public void buildGeometry(VertexConsumer vertex, Camera info, float partialTicks) {

        try {

            float rotationYaw = (-MinecraftClient.getInstance().player.yaw);
            float rotationPitch = MinecraftClient.getInstance().player.pitch;
            Vec3d view = info.getPos();
            float posX = (float) (MathHelper.lerp((double) partialTicks, this.prevPosX, this.x) - view.getX());
            float posY = (float) (MathHelper.lerp((double) partialTicks, this.prevPosY, this.y) - view.getY());
            float posZ = (float) (MathHelper.lerp((double) partialTicks, this.prevPosZ, this.z) - view.getZ());

            RenderSystem.pushMatrix();

            GL11.glTranslated(posX, posY - 0.5F, posZ);
            GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);

            GL11.glScaled(-1.0, -1.0, 1.0);
            GL11.glScaled(this.getBoundingBox()
                    .getXLength() * 0.04D, this.getBoundingBox()
                    .getYLength() * 0.04D,
                this.getBoundingBox()
                    .getZLength() * 0.04D
            );
            GL11.glScaled(1.0, 1.0, 1.0);

            RenderSystem.scaled(this.scale, this.scale, this.scale);

            TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;

            RenderSystem.disableColorMaterial();
            RenderSystem.disableLighting();
            RenderSystem.depthMask(false);
            RenderSystem.disableDepthTest();

            // idk todo
            fontRenderer.drawWithShadow(new MatrixStack(), this.text, 0, 0, element.format.getColorValue());

            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableLighting();
            RenderSystem.enableColorMaterial();

            RenderSystem.popMatrix();

            this.setBoundingBoxSpacing(1.0F, 1.0F);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if (ClientConfigs.INSTANCE.dmgParticleConfig.GROWS) {
            if (this.grow) {
                this.scale *= 1.05F;
                if (this.scale > ClientConfigs.INSTANCE.dmgParticleConfig.MAX_SIZE) {
                    this.grow = false;
                }
            } else {
                this.scale *= 0.97F;
            }
        }
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        }

        this.prevAngle = this.angle;
        this.angle += (float) Math.PI * 0.2 * 2.0F;
        if (this.onGround) {
            this.prevAngle = this.angle = 0.0F;
        }

        this.move(this.velocityX, this.velocityY, this.velocityZ);

        double speed = ClientConfigs.INSTANCE.dmgParticleConfig.SPEED;

        this.velocityY -= speed;
        this.velocityX += speed * world.random.nextDouble();
        this.velocityZ += speed * world.random.nextDouble();

        this.velocityY = Math.max(this.velocityY, -0.14D);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }
}
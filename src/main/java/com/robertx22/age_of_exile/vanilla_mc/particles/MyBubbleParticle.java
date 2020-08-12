package com.robertx22.age_of_exile.vanilla_mc.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

// i copy pasted this cus original bubble particle expires when not in water.
public class MyBubbleParticle extends SpriteBillboardParticle {
    private MyBubbleParticle(ClientWorld world, double x, double y, double z, double d, double e, double f) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.scale *= this.random.nextFloat() * 0.6F + 0.2F;
        this.velocityX = d * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.velocityY = e * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.velocityZ = f * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.maxAge = (int) (15 / (Math.random() * 0.8D + 0.2D));
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.velocityY += 0.005D;
        if (this.maxAge-- <= 0) {
            this.markDead();
        } else {
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.8500000238418579D;
            this.velocityY *= 0.8500000238418579D;
            this.velocityZ *= 0.8500000238418579D;

        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            MyBubbleParticle MyBubbleParticle = new MyBubbleParticle(clientWorld, d, e, f, g, h, i);
            MyBubbleParticle.setSprite(this.spriteProvider);
            return MyBubbleParticle;
        }
    }
}
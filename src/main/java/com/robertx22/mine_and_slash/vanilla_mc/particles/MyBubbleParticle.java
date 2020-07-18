package com.robertx22.mine_and_slash.vanilla_mc.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.world.World;



// i copy pasted this cus original bubble particle expires when not in water.
public class MyBubbleParticle extends SpriteBillboardParticle {

    private MyBubbleParticle(World world, double x, double y, double z, double xm, double ym, double zm) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.scale *= this.random.nextFloat() * 0.6F + 0.2F;
        this.velocityX = xm * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.velocityY = ym * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.velocityZ = zm * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0) {
            this.markDead();
        } else {
            this.velocityY += 0.002D;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= (double) 0.85F;
            this.velocityY *= (double) 0.85F;
            this.velocityZ *= (double) 0.85F;

        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteSet;

        public Factory(SpriteProvider sprite) {
            this.spriteSet = sprite;
        }

        public Particle makeParticle(DefaultParticleType type, World world, double x, double y, double z, double xm,
                                     double ym, double zm) {
            MyBubbleParticle bubbleparticle = new MyBubbleParticle(world, x, y, z, xm, ym, zm);
            bubbleparticle.setSprite(this.spriteSet);
            return bubbleparticle;
        }
    }
}
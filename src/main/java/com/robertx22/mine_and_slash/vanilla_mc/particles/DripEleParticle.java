package com.robertx22.mine_and_slash.vanilla_mc.particles;

import com.robertx22.mine_and_slash.uncommon.enumclasses.RGB;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DripEleParticle extends SpriteBillboardParticle {
    private final Fluid fluid;

    public DripEleParticle(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.gravityStrength = 0.06F;
        this.fluid = Fluids.FLOWING_WATER;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public int getColorMultiplier(float f) {
        return 240;
    }

    @Override
    public void tick() {

        float speedMulti = 1.4F;

        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.checkDeath();
        if (!this.dead) {
            this.velocityY -= (double) this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.onDeath();
            if (!this.dead) {
                this.velocityX *= 0.9800000190734863D * speedMulti;
                this.velocityY *= 0.9800000190734863D * speedMulti;
                this.velocityZ *= 0.9800000190734863D * speedMulti;
                BlockPos pos = new BlockPos(this.x, this.y, this.z);
                FluidState state = this.world.getFluidState(pos);
                if (state.getFluid() == this.fluid && this.y < (double) ((float) pos.getY() + state.getHeight(
                    this.world, pos))) {
                    this.markDead();
                }

            }
        }
    }

    protected void checkDeath() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }

    }

    protected void onDeath() {
    }

    @Environment(EnvType.CLIENT)
    public static class DrippingElementalFactory implements ParticleFactory<EleParticleData> {
        protected final SpriteProvider spriteProvider;

        public DrippingElementalFactory(SpriteProvider sprite) {
            this.spriteProvider = sprite;
        }

        @Override
        public Particle createParticle(EleParticleData type, World world, double x, double y, double z, double mx,
                                       double my, double mz) {
            DripEleParticle particle = new DripEleParticle(world, x, y, z);
            particle.gravityStrength = 0.01F;

            RGB col = type.element.getRGBColor();

            particle.setColor(col.getR(), col.getG(), col.getB());

            particle.setSprite(this.spriteProvider);
            return particle;
        }

    }

}

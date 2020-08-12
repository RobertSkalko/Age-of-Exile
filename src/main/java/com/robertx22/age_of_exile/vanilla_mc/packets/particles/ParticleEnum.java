package com.robertx22.age_of_exile.vanilla_mc.packets.particles;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.enumclasses.RGB;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GeometryUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public enum ParticleEnum {

    THORNS() {
        @Override
        public void activate(ParticlePacketData data, World world) {
            Vec3d center = getCenter(data);

            for (int i = 0; i < data.amount; i++) {
                Vec3d p = GeometryUtils.randomPos(center, world.random, data.radius);
                Vec3d m = GeometryUtils.randomMotion(center, world.random);

                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BIRCH_LEAVES.getDefaultState()),
                    p.x, p.y, p.z, m.x, m.y, m.z
                );
                world.addParticle(ParticleTypes.ITEM_SLIME, p.x, p.y, p.z, m.x, m.y, m.z);

            }
        }
    },

    PETRIFY() {
        @Override
        public void activate(ParticlePacketData data, World world) {
            Vec3d center = getCenter(data);

            for (int i = 0; i < data.amount; i++) {
                Vec3d p = GeometryUtils.randomPos(center, world.random, data.radius);
                Vec3d m = GeometryUtils.randomMotion(center, world.random);

                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.STONE.getDefaultState()), p.x, p.y,
                    p.z, m.x, m.y, m.z
                );
            }
        }
    },
    AOE() {
        @Override
        public void activate(ParticlePacketData data, World world) {
            Vec3d p = getCenter(data);

            for (int i = 0; i < data.amount; i++) {
                Vec3d r = GeometryUtils.getRandomPosInRadiusCircle(p.x, p.y, p.z, data.radius);
                world.addParticle(data.getParticleType(), r.x, r.y, r.z, data.mx, data.my, data.mz);
            }
        }
    },
    CIRCLE_REDSTONE() {
        @Override
        public void activate(ParticlePacketData data, World world) {
            Vec3d p = getCenter(data);

            for (int i = 0; i < data.radius * 60; i++) {
                Vec3d r = GeometryUtils.getRandomPosInRadiusCircle(p.x, p.y, p.z, data.radius);
                this.spawnRedstone(world, data.color, r.x, r.y, r.z);
            }
        }
    },

    NOVA_REDSTONE() {
        @Override
        public void activate(ParticlePacketData data, World world) {

            Vec3d p = getCenter(data);

            for (int i = 0; i < data.radius * 50; i++) {
                Vec3d r = GeometryUtils.getRandomHorizontalPosInRadiusCircle(p.x, p.y, p.z, data.radius);
                this.spawnRedstone(world, data.color, r.x, r.y, r.z);
            }
        }
    },
    NOVA() {
        @Override
        public void activate(ParticlePacketData data, World world) {

            Vec3d p = getCenter(data);

            for (int i = 0; i < data.amount; i++) {

                Vec3d r = GeometryUtils.getRandomHorizontalPosInRadiusCircle(p.x, p.y, p.z, data.radius);
                world.addParticle(data.getParticleType(), r.x, r.y, r.z, 0, 0, 0);
            }
        }
    },
    BLAZING_INFERNO() {
        @Override
        public void activate(ParticlePacketData data, World world) {

            for (int i = 0; i < 150; i++) {
                Vec3d p = GeometryUtils.getRandomHorizontalPosInRadiusCircle(data.getPos(), data.radius);
                world.addParticle(ParticleTypes.FLAME, p.x, p.y, p.z, 0, 0, 0);
                world.addParticle(ParticleTypes.SMOKE, p.x, p.y, p.z, 0, 0, 0);

            }

        }
    };

    ParticleEnum() {

    }

    public static void sendToClients(Entity source, ParticlePacketData data) {
        if (source.world.isClient) {
            try {
                data.type.activate(data, source.world);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MMORPG.sendToTracking(new ParticlePacket(data), source);
        }
    }

    public static void sendToClients(BlockPos pos, World world, ParticlePacketData data) {
        if (!world.isClient) {
            MMORPG.sendToTracking(new ParticlePacket(data), pos, world);
        } else {
            data.type.activate(data, world);
        }
    }

    public Vec3d getCenter(ParticlePacketData data) {
        if (data.isVecPos) {
            return data.getPos();
        } else {
            BlockPos pos = data.getBlockPos();
            return new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        }
    }

    public void spawnRedstone(World world, RGB color, double xpos, double ypos, double zpos) {

        DustParticleEffect data = new DustParticleEffect(color.getR(), color.getG(), color.getB(), 1F);
        world.addParticle(data, true, xpos, ypos, zpos, 1, 1, 1);
    }

    public abstract void activate(ParticlePacketData data, World world);

}

package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class GeometryUtils {

    public static Vec3d getRandomHorizontalPosInRadiusCircle(Vec3d pos, float radius) {
        return getRandomHorizontalPosInRadiusCircle(pos.x, pos.y, pos.z, radius);
    }

    public static Vec3d getRandomHorizontalPosInRadiusCircle(double x, double y, double z, float radius) {

        double u = Math.random();
        double v = Math.random();
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2 * v - 1);
        double xpos = x + (radius * Math.sin(phi) * Math.cos(theta));
        double ypos = y;
        double zpos = z + (radius * Math.cos(phi));

        return new Vec3d(xpos, ypos, zpos);

    }

    public static Vec3d getRandomPosInRadiusCircle(Vec3d p, float radius) {
        return getRandomPosInRadiusCircle(p.x, p.y, p.z, radius);
    }

    public static Vec3d randomMotion(Vec3d p, Random rand) {

        double x = rand.nextDouble() - 0.5D * 2;
        double y = -rand.nextDouble();
        double z = rand.nextDouble() - 0.5D * 2;

        return new Vec3d(x, y, z);
    }

    public static Vec3d randomPos(Vec3d p, Random rand, float radius) {

        double x = p.x + rand.nextDouble() * radius - 0.5D * radius;
        double y = p.y + rand.nextDouble() * radius - 0.5D * radius;
        double z = p.z + rand.nextDouble() * radius - 0.5D * radius;

        return new Vec3d(x, y, z);

    }

    public static Vec3d getRandomPosInRadiusCircle(double x, double y, double z, float radius) {

        double u = Math.random();
        double v = Math.random();
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2 * v - 1);
        double xpos = x + (radius * Math.sin(phi) * Math.cos(theta));
        double ypos = y + (radius * Math.sin(phi) * Math.sin(theta));
        double zpos = z + (radius * Math.cos(phi));

        return new Vec3d(xpos, ypos, zpos);

    }

}

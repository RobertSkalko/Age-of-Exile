package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class DamageParticle {
    public String str;

    public double x = 0;
    public double y = 0;
    public double z = 0;
    public double xPrev = 0;
    public double yPrev = 0;
    public double zPrev = 0;

    public int age = 0;

    public double ax = 0.00;
    public double ay = -0.005;
    public double az = 0.00;

    public double vx = 0;
    public double vy = 0;
    public double vz = 0;

    public Elements element;

    public DamageParticle(Entity entity, Elements element, String str) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Vec3d entityLocation = entity.getPos()
            .add(0, entity.getHeight(), 0);
        Vec3d cameraLocation = mc.gameRenderer.getCamera()
            .getPos();
        double offsetBy = entity.getWidth();
        Vec3d offset = cameraLocation.subtract(entityLocation)
            .normalize()
            .multiply(offsetBy);
        Vec3d pos = entityLocation.add(offset);

        age = 0;
        this.str = element.format + element.icon + Formatting.GRAY + str;
        this.element = element;

        vx = mc.world.random.nextGaussian() * 0.01;
        vy = 0.05 + (mc.world.random.nextGaussian() * 0.01);
        vz = mc.world.random.nextGaussian() * 0.01;

        x = pos.x;
        y = pos.y;
        z = pos.z;

        xPrev = x;
        yPrev = y;
        zPrev = z;
    }

    public void tick() {
        xPrev = x;
        yPrev = y;
        zPrev = z;
        age++;
        x += vx;
        y += vy;
        z += vz;
        vx += ax;
        vy += ay;
        vz += az;
    }

}
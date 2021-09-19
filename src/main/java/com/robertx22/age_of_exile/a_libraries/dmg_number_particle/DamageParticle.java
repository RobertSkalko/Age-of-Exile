package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.robertx22.age_of_exile.vanilla_mc.packets.DmgNumPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

public class DamageParticle {

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

    public String renderString = "";

    DmgNumPacket packet;

    public DamageParticle(Entity entity, DmgNumPacket packet) {
        this.packet = packet;

        this.renderString = packet.format + packet.string;
        if (packet.iscrit) {
            this.renderString += "!";
        }

        Minecraft mc = Minecraft.getInstance();
        Vector3d entityLocation = entity.position()
            .add(0, entity.getBbHeight(), 0);
        Vector3d cameraLocation = mc.gameRenderer.getMainCamera()
            .getPosition();
        double offsetBy = entity.getBbWidth();
        Vector3d offset = cameraLocation.subtract(entityLocation)
            .normalize()
            .scale(offsetBy);
        Vector3d pos = entityLocation.add(offset);

        age = 0;

        vx = mc.level.random.nextGaussian() * 0.01;
        vy = 0.05 + (mc.level.random.nextGaussian() * 0.01);
        vz = mc.level.random.nextGaussian() * 0.01;

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
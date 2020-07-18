package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle;

import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.packets.DmgNumPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class OnDisplayDamage {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void displayParticle(DmgNumPacket data) {

        mc = MinecraftClient.getInstance();

        World world = mc.player.world;
        double motionX = world.random.nextGaussian() * 0.01;
        double motionY = 0.5f;
        double motionZ = world.random.nextGaussian() * 0.01;
        Particle damageIndicator = new DamageParticle(
            Elements.valueOf(data.element), data.string, world, data.x, data.y + data.height, data.z, motionX,
            motionY, motionZ
        );

        MinecraftClient.getInstance().particleManager.addParticle(damageIndicator);

    }

}

package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class OnDisplayDamage {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void displayParticle(String element, String string, double x, double y, double z, float height) {

        mc = MinecraftClient.getInstance();

        World world = mc.player.world;
        double motionX = world.random.nextGaussian() * 0.01;
        double motionY = 0.5f;
        double motionZ = world.random.nextGaussian() * 0.01;
        Particle damageIndicator = new DamageParticle(
            Elements.valueOf(element), string, world, x, y + height, z, motionX,
            motionY, motionZ
        );

        MinecraftClient.getInstance().particleManager.addParticle(damageIndicator);

    }

}

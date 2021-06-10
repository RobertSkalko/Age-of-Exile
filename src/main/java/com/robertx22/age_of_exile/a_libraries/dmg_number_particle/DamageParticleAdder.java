package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.robertx22.age_of_exile.vanilla_mc.packets.DmgNumPacket;
import net.minecraft.entity.Entity;

public class DamageParticleAdder {

    public static void displayParticle(Entity entity, DmgNumPacket packet) {

        DamageParticleRenderer.PARTICLES.add(new DamageParticle(entity, packet));

    }

}

package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;

public class DamageParticleAdder {

    public static void displayParticle(Entity entity, String element, String string) {

        DamageParticleRenderer.PARTICLES.add(new DamageParticle(entity, Elements.valueOf(element), string));

    }

}

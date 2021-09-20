package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.particles.BasicParticleType;

public class ModParticles {

    public static void init() {

    }

    public static final RegObj<BasicParticleType> BUBBLE = Def.particle("bubble", new BasicParticleType(false));
    public static final RegObj<BasicParticleType> FLAME = Def.particle("flame", new BasicParticleType(false));
    public static final RegObj<BasicParticleType> POISON = Def.particle("poison", new BasicParticleType(false));
    public static final RegObj<BasicParticleType> FROST = Def.particle("frost", new BasicParticleType(false));
    public static final RegObj<BasicParticleType> BLOOD_DRIP = Def.particle("blood_drip", new BasicParticleType(false));

}

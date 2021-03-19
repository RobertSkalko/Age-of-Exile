package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public SoundEvent FREEZE = of("freeze");
    public SoundEvent DASH = of("dash");
    public SoundEvent SPLASH = of("splash");
    public SoundEvent STONE_CRACK = of("stone_crack");
    public SoundEvent FIREBALL = of("fireball");
    public SoundEvent BUFF = of("buff");
    public SoundEvent FIRE = of("fire");
    public SoundEvent BURN = of("burn");

    SoundEvent of(String id) {
        SoundEvent sound = new SoundEvent(new Identifier(Ref.MODID, id));
        Registry.register(Registry.SOUND_EVENT, new Identifier(Ref.MODID, id), sound);
        return sound;
    }

}

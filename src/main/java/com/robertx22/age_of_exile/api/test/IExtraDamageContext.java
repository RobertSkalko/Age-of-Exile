package com.robertx22.age_of_exile.api.test;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

/*
Every time you deal damage with spells you would need to implement this interface into the vanilla damage source,
and use that. So I suggest making your own damage source, and just doing something like:
new MyDamageSource(type, caster, new Identifier(modid,"fireball_direct"))

By knowing the ID of the damage, I can then allow a player to make a datapack for that damage ID,
where they can specify everything about the spell: the element, the damage calculation etc.

In other words, I only need spell mod devs to implement this interface, while the datapack work (which is a lot of balancing work),
can be left to players or modpack devs.
 */
public interface IExtraDamageContext {

    LivingEntity getDamageCaster();

    Identifier getDamageUniqueId();

}

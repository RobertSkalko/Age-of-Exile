package com.robertx22.age_of_exile.mobs;

import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.UUID;

public class OnTickRandomSpeedBoost {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4633-9556-521010E291A0");
    static EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.1D, EntityAttributeModifier.Operation.ADDITION);
    // attribute copied from enderman or zombifiedpiglin, idk which attribute it is..

    public static boolean onTickTryAnger(LivingEntity en, int ticksremaining) {
        if (en.world.isClient) {
            return false;
        }

        EntityAttributeInstance attri = en.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (ticksremaining < 1) {
            if (attri.hasModifier(ATTACKING_SPEED_BOOST)) {
                attri.removeModifier(ATTACKING_SPEED_BOOST);
            }
        }

        if (en.age % 60 == 0) {
            if (en.getAttacking() == null) {
                return false;
            }

            if (RandomUtils.roll(50)) {
                if (!attri.hasModifier(ATTACKING_SPEED_BOOST)) {
                    attri.addPersistentModifier(ATTACKING_SPEED_BOOST);
                    return true;
                }
            }
        }
        return false;
    }
}

package com.robertx22.age_of_exile.mobs;

import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.UUID;

public class OnTickRandomSpeedBoost {

    static EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(
        UUID.fromString("020E0DFB-87AE-4633-9556-521010E291A0"),
        "Attacking speed boost",
        0.075D,
        EntityAttributeModifier.Operation.ADDITION);
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

    public static void onAttackedAngerNearbyMobs(LivingEntity en, LivingEntity attacker) {
        if (en.world.isClient) {
            return;
        }
        if (!en.world.isDay()) {
            return;
        }

        if (attacker instanceof PlayerEntity) {

            List<MobEntity> entities = EntityFinder.start(
                en, MobEntity.class, en.getPos())
                .radius(40)
                .searchFor(EntityFinder.SearchFor.ALLIES)
                .build();

            entities.forEach(x -> {
                if (x.getAttacker() == null) {
                    x.setAttacker(attacker);
                }
            });

        }

    }

}
